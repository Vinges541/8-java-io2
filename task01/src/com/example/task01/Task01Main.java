package com.example.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task01Main {
    public static void main(String[] args) {
        //здесь вы можете вручную протестировать ваше решение, вызывая реализуемый метод и смотря результат
        // например вот так:

        /*
        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));
        */
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        String[] args = {"ffprobe", "-v", "error", "-of", "flat", "-show_format", file.toString()};
        ProcessBuilder processBuilder = new ProcessBuilder(args)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .redirectOutput(ProcessBuilder.Redirect.PIPE);
        Process process = processBuilder.start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Process exited with code " + exitCode);
            return null;
        }
        Pattern pattern = Pattern.compile("format\\.tags\\.title=\"(.+)\"");
        String result = null;
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                result = matcher.group(1);
                break;
            }
        }
        return result;
    }
}
