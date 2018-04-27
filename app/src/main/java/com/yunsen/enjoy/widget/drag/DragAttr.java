package com.yunsen.enjoy.widget.drag;

/**
 * Created by Administrator on 2018/4/5.
 */
public class DragAttr {
    private int startX;
    private int endX;
    private int startY;
    private int endY;
    private boolean closeImgHas = true;
    private boolean canMove;
    private boolean dragHasVis = true;

    public DragAttr(int startX, int endX, int startY, int endY) {
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public boolean isCloseImgHas() {
        return closeImgHas;
    }

    public void setCloseImgHas(boolean closeImgHas) {
        this.closeImgHas = closeImgHas;
    }

    public boolean getCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isDragHasVis() {
        return dragHasVis;
    }

    public void setDragHasVis(boolean dragHasVis) {
        this.dragHasVis = dragHasVis;
    }

    public void saveLayout(int oldXStart, int oldYStart, int oldXEnd, int oldYEnd) {
        this.startX = oldXStart;
        this.startY = oldYStart;
        this.endX = oldXEnd;
        this.endY = oldYEnd;
    }

    public boolean needLayout() {
        return startX != endX;
    }
}
