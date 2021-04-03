package webview.resize;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Util class to handle window resizing when a stage style set to StageStyle.UNDECORATED.
 * Provide addResizeListener methods to pass a stage and listen to its events
 * Support drag and drop events
 * See: https://stackoverflow.com/questions/19455059/allow-user-to-resize-an-undecorated-stage
 * @aduforat
 */
public class ResizeHelper {

    private ResizeHelper() {
    	
    }
    
    /**
     * 
     * @param stage the undecorated stage to resize and drag
     * @param borderSize the border size in pixel in which you can click to resize
     */
    public static void addResizeListener(Stage stage, int borderSize) {
        ResizeListener resizeListener = new ResizeListener(stage, borderSize);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeListener);

        ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node child : children) {
            addListenerDeeply(child, resizeListener);
        }
    }

    private static void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, listener);
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            ObservableList<Node> children = parent.getChildrenUnmodifiable();
            for (Node child : children) {
                addListenerDeeply(child, listener);
            }
        }
    }

    static class ResizeListener implements EventHandler<MouseEvent> {
        private Stage stage;
        private Cursor cursorEvent = Cursor.DEFAULT;
        private int borderSize;
        private double startX = 0;
        private double startY = 0;
        private double xOffset = 0;
        private double yOffset = 0;

        public ResizeListener(Stage stage, int borderSize) {
            this.stage = stage;
            this.borderSize = borderSize;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            final EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
            Scene scene = stage.getScene();

            final double mouseEventX = mouseEvent.getSceneX();
            final double mouseEventY = mouseEvent.getSceneY();

            if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
                updateCursorKind(scene, mouseEventX, mouseEventY);
            } else if (MouseEvent.MOUSE_EXITED.equals(mouseEventType) || MouseEvent.MOUSE_EXITED_TARGET.equals(mouseEventType)) {
                scene.setCursor(Cursor.DEFAULT);
            } else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
                updateStartAndOffset(mouseEvent, mouseEventX, mouseEventY);
            } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {
                onDrop(mouseEvent, mouseEventX, mouseEventY);
            }
        }

		private void onDrop(MouseEvent mouseEvent, final double mouseEventX, final double mouseEventY) {
			if (!Cursor.DEFAULT.equals(cursorEvent)) {
			    resizeWindow(mouseEvent, mouseEventX, mouseEventY);
			} else if (mouseEvent.getSceneY() < 70) {
			    moveStage(mouseEvent);
			}
		}

		private void moveStage(MouseEvent mouseEvent) {
			stage.setX(mouseEvent.getScreenX() - xOffset);
			stage.setY(mouseEvent.getScreenY() - yOffset);
		}
		
		private void resizeWindow(MouseEvent mouseEvent, final double mouseEventX, final double mouseEventY) {
			if (!Cursor.W_RESIZE.equals(cursorEvent) && !Cursor.E_RESIZE.equals(cursorEvent)) {
			    verticalResize(mouseEvent, mouseEventY);
			}
			if (!Cursor.N_RESIZE.equals(cursorEvent) && !Cursor.S_RESIZE.equals(cursorEvent)) {
			    horizontalResize(mouseEvent, mouseEventX);
			}
		}

		private void updateCursorKind(Scene scene, double mouseEventX, double mouseEventY) {
			final CursorPos cursorPos = new CursorPos(scene, mouseEventX, mouseEventY, borderSize);
			cursorEvent = cursorPos.getCursorKind();
			scene.setCursor(cursorEvent);
		}
		
		private void updateStartAndOffset(MouseEvent mouseEvent, final double mouseEventX, final double mouseEventY) {
			startX = stage.getWidth() - mouseEventX;
			startY = stage.getHeight() - mouseEventY;
			xOffset = mouseEvent.getSceneX();
			yOffset = mouseEvent.getSceneY();
		}

		private void horizontalResize(MouseEvent mouseEvent, double mouseEventX) {
			final double minResizeWidth = stage.getMinWidth() > (borderSize * 2) ? stage.getMinWidth() : (borderSize * 2);
			if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.W_RESIZE.equals(cursorEvent) || Cursor.SW_RESIZE.equals(cursorEvent)) {
			    leftHorizontalResize(mouseEvent, mouseEventX, minResizeWidth);
			} else {
			    rightHorizontalResize(mouseEventX, minResizeWidth);
			}
		}

		private void leftHorizontalResize(MouseEvent mouseEvent, double mouseEventX, final double minResizeWidth) {
			if (stage.getWidth() > minResizeWidth || mouseEventX < 0) {
			    stage.setWidth(stage.getX() - mouseEvent.getScreenX() + stage.getWidth());
			    stage.setX(mouseEvent.getScreenX());
			}
		}
		
		private void rightHorizontalResize(double mouseEventX, final double minResizeWidth) {
			if (stage.getWidth() > minResizeWidth || mouseEventX + startX - stage.getWidth() > 0) {
			    stage.setWidth(mouseEventX + startX);
			}
		}
		
		private void verticalResize(MouseEvent mouseEvent, double mouseEventY) {
			final double minResizeHeight = stage.getMinHeight() > (borderSize * 2) ? stage.getMinHeight() : (borderSize * 2);
			if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.N_RESIZE.equals(cursorEvent) || Cursor.NE_RESIZE.equals(cursorEvent)) {
			    topVerticalResize(mouseEvent, mouseEventY, minResizeHeight);
			} else {
			    bottomVerticalResize(mouseEventY, minResizeHeight);
			}
		}

		private void bottomVerticalResize(double mouseEventY, final double minResizeHeight) {
			if (stage.getHeight() > minResizeHeight || mouseEventY + startY - stage.getHeight() > 0) {
			    stage.setHeight(mouseEventY + startY);
			}
		}

		private void topVerticalResize(MouseEvent mouseEvent, double mouseEventY, final double minResizeHeight) {
			if (stage.getHeight() > minResizeHeight || mouseEventY < 0) {
			    stage.setHeight(stage.getY() - mouseEvent.getScreenY() + stage.getHeight());
			    stage.setY(mouseEvent.getScreenY());
			}
		}
    }
}