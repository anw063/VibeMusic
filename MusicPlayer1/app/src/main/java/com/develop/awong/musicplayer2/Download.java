package com.develop.awong.musicplayer2;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by parkn on 3/6/2018.
 */

public final class Download{
    private DownloadManager downloadManager;
    public Download(Context v) {
        downloadManager = (DownloadManager)v.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public long DownloadData (Uri uri,Context context, String songName) {

        long downloadReference;

        // Create request for android download manager
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setTitle("Song Download")
                .setDescription("Song download via MusicPlayer.")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverRoaming(false)
                .setDestinationInExternalFilesDir(context,Environment.DIRECTORY_DOWNLOADS,songName);

        request.allowScanningByMediaScanner();

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);
        return downloadReference;
    }

    public String DownloadStatus(long DownloadId){

        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(DownloadId);
        Cursor cursor = downloadManager.query(query);

        //column for download  status
        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = cursor.getInt(columnIndex);
        //column for reason code if the download failed or paused
        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = cursor.getInt(columnReason);
        //get the download filename
        int filenameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
        String filename = cursor.getString(filenameIndex);

        String statusText = "";
        String reasonText = "";

        switch(status){
            case DownloadManager.STATUS_FAILED:
                statusText = "STATUS_FAILED";
                switch(reason){
                    case DownloadManager.ERROR_CANNOT_RESUME:
                        reasonText = "ERROR_CANNOT_RESUME";
                        break;
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        reasonText = "ERROR_DEVICE_NOT_FOUND";
                        break;
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        reasonText = "ERROR_FILE_ALREADY_EXISTS";
                        break;
                    case DownloadManager.ERROR_FILE_ERROR:
                        reasonText = "ERROR_FILE_ERROR";
                        break;
                    case DownloadManager.ERROR_HTTP_DATA_ERROR:
                        reasonText = "ERROR_HTTP_DATA_ERROR";
                        break;
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        reasonText = "ERROR_INSUFFICIENT_SPACE";
                        break;
                    case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                        reasonText = "ERROR_TOO_MANY_REDIRECTS";
                        break;
                    case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                        reasonText = "ERROR_UNHANDLED_HTTP_CODE";
                        break;
                    case DownloadManager.ERROR_UNKNOWN:
                        reasonText = "ERROR_UNKNOWN";
                        break;
                }
                break;
            case DownloadManager.STATUS_PAUSED:
                statusText = "STATUS_PAUSED";
                switch(reason){
                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                        reasonText = "PAUSED_QUEUED_FOR_WIFI";
                        break;
                    case DownloadManager.PAUSED_UNKNOWN:
                        reasonText = "PAUSED_UNKNOWN";
                        break;
                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                        reasonText = "PAUSED_WAITING_FOR_NETWORK";
                        break;
                    case DownloadManager.PAUSED_WAITING_TO_RETRY:
                        reasonText = "PAUSED_WAITING_TO_RETRY";
                        break;
                }
                break;
            case DownloadManager.STATUS_PENDING:
                statusText = "STATUS_PENDING";
                break;
            case DownloadManager.STATUS_RUNNING:
                statusText = "STATUS_RUNNING";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                statusText = "STATUS_SUCCESSFUL";
                reasonText = "Filename:\n" + filename;
                break;
        }

        return "Music Download Status:" + "\n" + statusText + "\n" + reasonText;
        /*Toast toast = Toast.makeText(context,
                "Music Download Status:" + "\n" + statusText + "\n" +
                        reasonText,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 25, 400);
        toast.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 3000);*/
    }

    public void cancelDownload (long downloadID) {
        downloadManager.remove(downloadID);
    }
}