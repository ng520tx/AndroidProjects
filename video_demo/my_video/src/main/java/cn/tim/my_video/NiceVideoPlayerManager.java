package cn.tim.my_video;

public class NiceVideoPlayerManager {

    private NiceVideoPlayer mVideoPlayer;

    private NiceVideoPlayerManager() {
    }

    private static NiceVideoPlayerManager sInstance;

    public static synchronized NiceVideoPlayerManager instance() {
        if (sInstance == null) {
            sInstance = new NiceVideoPlayerManager();
        }
        return sInstance;
    }

    public void setCurrentNiceVideoPlayer(NiceVideoPlayer videoPlayer) {
        mVideoPlayer = videoPlayer;
    }

    public void releaseNiceVideoPlayer() {
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
            mVideoPlayer = null;
        }
    }

    public boolean onBackPressd() {
        if (mVideoPlayer != null) {
            if (mVideoPlayer.isFullScreen()) {
                return mVideoPlayer.exitFullScreen();
            } else if (mVideoPlayer.isTinyWindow()) {
                return mVideoPlayer.exitTinyWindow();
            } else {
                mVideoPlayer.release();
                return false;
            }
        }
        return false;
    }
}
