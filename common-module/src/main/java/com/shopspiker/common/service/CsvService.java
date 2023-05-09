package com.shopspiker.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CsvService {

    public <T> Resource generateCsvFile(List<T> clazz, List<String> noNeededColumn) throws IOException {
        List<Method> methods = getMethodsList(clazz, noNeededColumn);
        String[] extractHeader = getHeader(methods);

        StringWriter st = new StringWriter();
        try (ICsvMapWriter mapWriter = new CsvMapWriter(st, CsvPreference.EXCEL_PREFERENCE)) {
            mapWriter.writeHeader(extractHeader);
            for (final T obj : clazz) {
                Map<String, Object> mapPrices = buildMapLine(methods, obj);
                mapWriter.write(mapPrices, extractHeader, new CellProcessor[extractHeader.length]);
            }
        }
        return (new InputStreamResource(new ByteArrayInputStream(st.toString().getBytes(StandardCharsets.UTF_8))));
    }


    private <T> Map<String, Object> buildMapLine(List<Method> methods, T obj) {
        Map<String, Object> output = new HashMap<String, Object>();
        for (Method method : methods) {
            try {
                output.put(getColumnName(method), obj.getClass().getMethod(method.getName()).invoke(obj));
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                     | NoSuchMethodException | SecurityException e) {
                log.error(e.getLocalizedMessage());
            }
        }
        return output;
    }


    private <T> List<Method> getMethodsList(List<T> clazz, List<String> noNeededColumn) {
        return Arrays.stream(clazz.get(0).getClass().getMethods())
                .parallel()
                .filter(method -> (method.getName().startsWith("get") || method.getName().startsWith("is"))
                        && !"getClass".equals(method.getName())
                        && !noNeededColumn.contains(getColumnName(method)))
                .sorted(Comparator.comparing(this::getColumnName))
                .collect(Collectors.toList());
    }

    private String[] getHeader(List<Method> methods) {
        return methods.stream()
                .map(this::getColumnName)
                .toArray(String[]::new);
    }

    private String getColumnName(Method method) {
        if (method.getName().startsWith("get"))
            return StringUtils.uncapitalize(method.getName().substring(3));
        else if (method.getName().startsWith("is"))
            return StringUtils.uncapitalize(method.getName().substring(2));
        else
            return "";
    }

}
