package org.telegram.api.engine.file;

import org.telegram.api.input.filelocation.TLAbsInputFileLocation;
import org.telegram.api.storage.file.TLAbsFileType;

/**
 * Created by Ruben Bermudez on 18.11.13.
 */
public interface DownloadListener {
    /**
     * On part downloaded.
     *
     * @param percent the percent
     * @param downloadedSize the downloaded size
     */
    void onPartDownloaded(int percent, int downloadedSize);

    /**
     * On downloaded.
     *
     * @param task the task
     */
    void onDownloaded(Downloader.DownloadTask task);
    
    void onDownloaded(TLAbsInputFileLocation location, int size, String fileName, TLAbsFileType type);

    /**
     * On failed.
     */
    void onFailed();
}
