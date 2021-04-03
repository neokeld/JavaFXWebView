package webview.resize;

import javafx.scene.Cursor;
import javafx.scene.Scene;

public class CursorPos {

    double sceneWidth;
    double sceneHeight;
	double mouseEventX;
	double mouseEventY;
	private int borderSize;
	
	public CursorPos(Scene scene, double mouseEventX, double mouseEventY, int borderSize) {
        this.sceneWidth = scene.getWidth();
        this.sceneHeight = scene.getHeight();
		this.borderSize = borderSize;
		this.mouseEventX = mouseEventX;
		this.mouseEventY = mouseEventY;
	}
	
	public boolean nw() {
		return mouseEventX < borderSize && mouseEventY < borderSize;
	}
	
	public boolean sw() {
		return mouseEventX < borderSize && mouseEventY > sceneHeight - borderSize;
	}
	
	public boolean ne() {
		return mouseEventX > sceneWidth - borderSize && mouseEventY < borderSize;
	}
	
	public boolean se() {
		return mouseEventX > sceneWidth - borderSize && mouseEventY > sceneHeight - borderSize;
	}
	
	public boolean w() {
		return mouseEventX < borderSize;
	}
	
	public boolean e() {
		return mouseEventX > sceneWidth - borderSize;
	}

	public boolean n() {
		return mouseEventY < borderSize;
	}
	
	public boolean s() {
		return mouseEventY > sceneHeight - borderSize;
	}

	public Cursor getCursorKind() {
		Cursor cursor;
		if (this.nw()) {
		    cursor = Cursor.NW_RESIZE;
		} else if (this.sw()) {
			cursor = Cursor.SW_RESIZE;
		} else if (this.ne()) {
			cursor = Cursor.NE_RESIZE;
		} else if (this.se()) {
			cursor = Cursor.SE_RESIZE;
		} else if (this.w()) {
			cursor = Cursor.W_RESIZE;
		} else if (this.e()) {
			cursor = Cursor.E_RESIZE;
		} else if (this.n()) {
			cursor = Cursor.N_RESIZE;
		} else if (this.s()) {
			cursor = Cursor.S_RESIZE;
		} else {
			cursor = Cursor.DEFAULT;
		}
		return cursor;
	}
}
