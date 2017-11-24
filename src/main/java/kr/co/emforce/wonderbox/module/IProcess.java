package kr.co.emforce.wonderbox.module;

public interface IProcess {
  public final String MODULES_DIR = ((System.getProperty("modules.dir") == null) ? "" : System.getProperty("modules.dir"));
  public final String DOWNLOAD_URL = ((System.getProperty("download.url") == null) ? "" : System.getProperty("download.url"));
  public final String RANK_HISTORY_DIR = ((System.getProperty("rankhistory.url") == null) ? "" : System.getProperty("rankhistory.url"));
  
  public static final String UPLOADS_DIR = System.getProperty("uploads.dir");
  public static final String DOWNLOADS_DIR = System.getProperty("downloads.dir");
  
  public static final String BETA = "beta";
  public static final String PRODUCTION = "production";

  public final String AUTO_BID_WORKER = "autobid-worker.sh";
  public final String CPA_AUTO_BID_WORKER = "cpa_autobid-worker.sh";
  public final String KWD_RANK_HISTORIES_WORKER = "kwd_rank_histories-worker.sh";
}
