package org.telegram.util;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Ruben Bermudez
 * @version 2.0
 * @brief Logger to file
 * @date 21/01/15
 */
public class BotLogger {
  private static final Logger logger = Logger.getLogger("org.telegram.");

  private static SimpleDateFormat dateFormatter = new SimpleDateFormat("[yyyy-MM-dd'T'HH:mm:ss.SSSZ]");
  private static SimpleDateFormat dateFormatterForFileName = new SimpleDateFormat("yyyyMMdd");
  private static final Object lockToWrite = new Object();
  private static volatile PrintWriter logginFile;
  private static volatile String currentFileName;
  private static final String pathToLogs = "./";
  private static volatile Date lastFileDate;


  public static void log(Level level, String tag, String msg) {
    logger.log(level, String.format("[%s] %s", tag, msg));
  }


  public static void severe(String tag, String msg) {
    logger.fatal(String.format("[%s] %s", tag, msg));
  }

  public static void warn(String tag, String msg) {
    warning(tag, msg);
  }

  public static void debug(String tag, String msg) {
    fine(tag, msg);
  }

  public static void error(String tag, String msg) {
    severe(tag, msg);
  }

  public static void trace(String tag, String msg) {
    finer(tag, msg);
  }

  public static void warning(String tag, String msg) {
    logger.warn(String.format("[%s] %s", tag, msg));
  }


  public static void info(String tag, String msg) {
    logger.info(String.format("[%s] %s", tag, msg));
  }


  public static void config(String tag, String msg) {
    logger.info(String.format("[%s] %s", tag, msg));
  }


  public static void fine(String tag, String msg) {
    logger.trace(String.format("[%s] %s", tag, msg));
  }


  public static void finer(String tag, String msg) {
    logger.trace(String.format("[%s] %s", tag, msg));
  }


  public static void finest(String tag, String msg) {
    logger.trace(String.format("[%s] %s", tag, msg));
  }


  public static void log(Level level, String tag, Throwable throwable) {
    logger.log(level, String.format("[%s] Exception", tag), throwable);
  }

  public static void log(Level level, String tag, String msg, Throwable thrown) {
    logger.log(level, msg, thrown);
  }

  public static void severe(String tag, Throwable throwable) {
    logger.fatal(tag,throwable);
  }

  public static void warning(String tag, Throwable throwable) {
    logger.warn(tag,throwable);
  }

  public static void info(String tag, Throwable throwable) {
    logger.info(tag,throwable);
  }

  public static void config(String tag, Throwable throwable) {
    logger.info(tag,throwable);
  }

  public static void fine(String tag, Throwable throwable) {
    logger.trace(tag,throwable);
  }

  public static void finer(String tag, Throwable throwable) {
    logger.trace(tag,throwable);
  }

  public static void finest(String tag, Throwable throwable) {
    logger.trace(tag,throwable);
  }

  public static void warn(String tag, Throwable throwable) {
    warning(tag, throwable);
  }

  public static void debug(String tag, Throwable throwable) {
    fine(tag, throwable);
  }

  public static void error(String tag, Throwable throwable) {
    severe(tag, throwable);
  }

  public static void trace(String tag, Throwable throwable) {
    finer(tag, throwable);
  }

  public static void severe(String msg, String tag, Throwable throwable) {
    log(Level.FATAL, tag, msg, throwable);
  }

  public static void warning(String msg, String tag, Throwable throwable) {
    log(Level.WARN, tag, msg, throwable);
  }

  public static void info(String msg, String tag, Throwable throwable) {
    log(Level.INFO, tag, msg, throwable);
  }

  public static void config(String msg, String tag, Throwable throwable) {
    log(Level.INFO, tag, msg, throwable);
  }

  public static void fine(String msg, String tag, Throwable throwable) {
    log(Level.TRACE, tag, msg, throwable);
  }

  public static void finer(String msg, String tag, Throwable throwable) {
    log(Level.TRACE, tag, msg, throwable);
  }

  public static void finest(String msg, String tag, Throwable throwable) {
    log(Level.TRACE, msg, throwable);
  }

  public static void warn(String msg, String tag, Throwable throwable) {
    logger.warn(tag,throwable);
  }

  public static void debug(String msg, String tag, Throwable throwable) {
    log(Level.TRACE, tag, msg, throwable);
  }

  public static void error(String msg, String tag, Throwable throwable) {
    logger.error(tag,throwable);
  }

  public static void trace(String msg, String tag, Throwable throwable) {
    log(Level.TRACE, tag, msg, throwable);
  }

  private static boolean isCurrentDate(Date dateTime) {
    return dateTime.getTime() == lastFileDate.getTime();
  }


  private static String dateFormatterForFileName(Date dateTime) {
    return dateFormatterForFileName.format(dateTime);
  }

  private static String dateFormatterForLogs(Date dateTime) {
    return dateFormatter.format(dateTime);
  }



}
