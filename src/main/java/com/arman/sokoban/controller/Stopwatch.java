package com.arman.sokoban.controller;

public class Stopwatch {

    private static boolean started;
    private static boolean stopped;
    private static long elapsedTime;

    private static Thread timerThread;

    private Stopwatch() {

    }

    public static long elapsedMillis() {
        return elapsedTime;
    }

    public static long elapsedSeconds() {
        return elapsedMillis() / 1000;
    }

    public static long elapsedMinutes() {
        return elapsedSeconds() / 60;
    }

    public static long elapsedHours() {
        return elapsedMinutes() / 60;
    }

    public static String formatToHours() {
        return formatToHours(elapsedMillis());
    }

    public static String formatToMinutes() {
        return formatToMinutes(elapsedMillis());
    }

    public static String formatToSeconds() {
        return formatToSeconds(elapsedMillis());
    }

    public static String formatToHours(long millis) {
        long seconds = (millis / 1000) % 60;
        long minutes = (millis / (1000 * 60)) % 60;
        long hours = (millis / (1000 * 60 * 60)) % 24;
        long decis = (millis % 1000) / 10;
        String time = String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, decis);
        return time;
    }

    public static String formatToMinutes(long millis) {
        long seconds = (millis / 1000) % 60;
        long minutes = (millis / (1000 * 60)) % 60;
        long decis = (millis % 1000) / 10;
        String time = String.format("%02d:%02d:%02d", minutes, seconds, decis);
        return time;
    }

    public static String formatToSeconds(long millis) {
        long seconds = (millis / 1000) % 60;
        long decis = (millis % 1000) / 10;
        String time = String.format("%02d:%02d", seconds, decis);
        return time;
    }

    public static void restart() {
        stop();
        timerThread = new Thread(() -> {
            while (started) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }
                if (!stopped) {
                    elapsedTime += 1;
                }
            }
        });
        started = true;
        stopped = false;
        timerThread.start();
    }

    public static void start() {
        restart();
    }

    public static void stop() {
        started = false;
        stopped = true;
        if (timerThread != null) {
            timerThread.interrupt();
            timerThread = null;
        }
        elapsedTime = 0;
    }

    public static void pause() {
        if (started) {
            stopped = true;
        }
    }

    public static void resume() {
        if (started) {
            stopped = false;
        } else {
            start();
        }
    }

}
