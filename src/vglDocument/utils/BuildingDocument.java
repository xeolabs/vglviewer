/* Generated by Together */

package vglDocument.utils;

import java.util.Iterator;
import vglDocument.VGLPoint;
import vglDocument.VGLPolygon;
import vglDocument.VGLProperties;

class BuildingDocument extends AbstractBuilderState {
    public BuildingDocument(BuildContext context) {
        super(context, null); // no parent
    }

    public AbstractBuilderState openStyle() {
        return new BuildingStyle(this.getContext(), this);
    }

    public AbstractBuilderState openPolygon() {
        return new BuildingPolygon(this.getContext(), this);
    }

    public AbstractBuilderState openPath() {
        return new BuildingPath(this.getContext(), this);
    }

    public AbstractBuilderState openText() {
        return new BuildingText(this.getContext(), this);
    }

    public AbstractBuilderState openCircle() {
        return new BuildingCircle(this.getContext(), this);
    }

    public AbstractBuilderState close() {
        return this; // no parent
    }

    protected String getDescription() {
        return "a VGL document";
    }
}
