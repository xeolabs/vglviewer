package com.neocoders.vglviewer.sdaz;

public interface SDAZSubject {
    public void setIndicator(int x, int y, int width, int height);

    public SDAZViewport getViewport();

    public void setViewport(SDAZViewport volume);

    public SDAZWindow getWindow();

    public void update();
}
