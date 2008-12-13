/* Generated by Together */

package com.neocoders.vglviewer.boundaryMap;

import java.util.Vector;

/**
 *Defines contract for a display list element. The element must provide it's enclosing boundary, and allow itself to split
 * along either the X or Y axis into two lists of fragments, one list of those on one side of the dividing line, the other
 * list of fragments on the other side of the dividing line.
 */
public interface BoundaryMapElement {
    /** Get boundary of this element */
    public Boundary getBoundary();

    /**
     * Split this element about line at given position on given axis; if the line intersects the element, this method will
     * return true and the leftFragments and rightFragments lists will contain the fragments on either
     * side of the dividing line, otherwise this method will return false.
     */
    public boolean split(int axis, long pos, Vector leftFragments, Vector rightFragments);

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @supplierRole boundary 
     */
    /*# Boundary lnkBoundary; */
}