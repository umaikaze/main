package clzzz.helper.ui;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Custom class used for resizing the window after stage style is set to transparent
 *
 * Credit (StackOverflow)
 * @author Alexander.Berg
 * @author Evgenii Kanivets
 * @author Zachary Perales
 */
public class ResizeHelper {

    private static final int CORNERDADIUS = 20;
    private static final int BORDERTHICKNESS = 2;

    private static boolean doNotMoveWindow = false;

    /**
     * Adds resize listener that allows resize even if stage style is set to TRANSPARENT
     * allows the user to drag the window at any part that is not a menu button, text field or scrollbar
     * @param stage must be already displayed
     */
    public static void addResizeListener(Stage stage) {
        addResizeListener(stage, 1, 1, Double.MAX_VALUE, Double.MAX_VALUE);
    }

    /**
     * Adds resize listener with specified maximum and minimum sizes
     * @param stage must be already displayed
     */
    public static void addResizeListener(Stage stage, double minWidth, double minHeight, double maxWidth,
                                         double maxHeight) {
        ResizeListener resizeListener = new ResizeListener(stage);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeListener);

        resizeListener.setMinWidth(minWidth);
        resizeListener.setMinHeight(minHeight);
        resizeListener.setMaxWidth(maxWidth);
        resizeListener.setMaxHeight(maxHeight);

        ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node child : children) {
            doNotMoveWindow = isUndraggable(resizeListener, child);
        }
    }

    /**
     * Allows the listener to detect if it is on an undraggable node even if the user is dragging the child of the
     * undraggable node
     */
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
                doNotMoveWindow = isUndraggable(listener, child);
            }
        }
    }

    /**
     * Determines if the current node should not be dragged
     */
    private static boolean isUndraggable(EventHandler<MouseEvent> listener, Node child) {
        if (child instanceof ScrollBar || child instanceof MenuButton || child instanceof TextField) {
            return true;
        } else {
            addListenerDeeply(child, listener);
            return false;
        }
    }

    /**
     * ResierListener class that handles both resize and dragging actions on given stage
     */
    static class ResizeListener implements EventHandler<MouseEvent> {
        private final Stage stage;

        private Cursor cursorEvent = Cursor.DEFAULT;
        private boolean resizing = true;
        private int border = 4;
        private double startX = 0;
        private double startY = 0;
        private double screenOffsetX = 0;
        private double screenOffsetY = 0;

        // Max and min sizes for controlled stage
        private double minWidth;
        private double maxWidth;
        private double minHeight;
        private double maxHeight;

        /**
         * Initialize the ResizeLister to given stage
         * @param stage must be already displayed, else NullPointerException will be thrown
         */
        public ResizeListener(Stage stage) {
            this.stage = stage;
        }

        /**
         * @param minWidth minimum width
         */
        public void setMinWidth(double minWidth) {
            this.minWidth = minWidth;
        }

        /**
         * @param maxWidth maximum width
         */
        public void setMaxWidth(double maxWidth) {
            this.maxWidth = maxWidth;
        }

        /**
         * @param minHeight minimum height
         */
        public void setMinHeight(double minHeight) {
            this.minHeight = minHeight;
        }

        /**
         * @param maxHeight maximum height
         */
        public void setMaxHeight(double maxHeight) {
            this.maxHeight = maxHeight;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
            Scene scene = stage.getScene();

            double mouseEventX = mouseEvent.getSceneX();
            double mouseEventY = mouseEvent.getSceneY();
            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();

            if (MouseEvent.MOUSE_MOVED.equals(mouseEventType) && !stage.isMaximized()) {
                handleMouseMovement(scene, mouseEventX, mouseEventY, sceneWidth, sceneHeight);
            } else if (MouseEvent.MOUSE_EXITED.equals(mouseEventType)
                    || MouseEvent.MOUSE_EXITED_TARGET.equals(mouseEventType)) {
                scene.setCursor(Cursor.DEFAULT);
            } else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
                handleMouseExit(mouseEventX, mouseEventY);
            } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType) && !Cursor.DEFAULT.equals(cursorEvent)) {
                handleResize(mouseEvent, mouseEventX, mouseEventY);
            }

            if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType) && Cursor.DEFAULT.equals(cursorEvent)) {
                setScreenOffset(mouseEvent);
            }

            if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType) && Cursor.DEFAULT.equals(cursorEvent) && !resizing) {
                moveStage(mouseEvent);
            }
        }

        private void setScreenOffset(MouseEvent mouseEvent) {
            resizing = false;
            screenOffsetX = stage.getX() - mouseEvent.getScreenX();
            screenOffsetY = stage.getY() - mouseEvent.getScreenY();
        }

        private void moveStage(MouseEvent mouseEvent) {
            stage.setX(mouseEvent.getScreenX() + screenOffsetX);
            stage.setY(mouseEvent.getScreenY() + screenOffsetY);
        }

        private void handleMouseMovement(Scene scene, double mouseEventX, double mouseEventY, double sceneWidth,
                                         double sceneHeight) {
            cursorEvent = getCursorEventType(mouseEventX, mouseEventY, sceneWidth, sceneHeight);
            scene.setCursor(cursorEvent);
        }

        private void handleMouseExit(double mouseEventX, double mouseEventY) {
            startX = stage.getWidth() - mouseEventX;
            startY = stage.getHeight() - mouseEventY;
        }

        /**
         * Resizes the windows based on the current mouse event type and mouse position
         */
        private void handleResize(MouseEvent mouseEvent, double mouseEventX, double mouseEventY) {
            resizing = true;
            if (!Cursor.W_RESIZE.equals(cursorEvent) && !Cursor.E_RESIZE.equals(cursorEvent)) {
                handleVerticalResize(mouseEvent, mouseEventY);
            }

            if (!Cursor.N_RESIZE.equals(cursorEvent) && !Cursor.S_RESIZE.equals(cursorEvent)) {
                handleHorizontalResize(mouseEvent, mouseEventX);
            }
            resizing = false;
        }

        /**
         * Used when mouse event is on the left or right
         */
        private void handleHorizontalResize(MouseEvent mouseEvent, double mouseEventX) {
            double minWidth = stage.getMinWidth() > (border * 2) ? stage.getMinWidth() : (border * 2);
            if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.W_RESIZE.equals(cursorEvent)
                    || Cursor.SW_RESIZE.equals(cursorEvent)) {
                if (stage.getWidth() > minWidth || mouseEventX < 0) {
                    setStageWidth(stage.getX() - mouseEvent.getScreenX() + stage.getWidth());
                    stage.setX(mouseEvent.getScreenX());
                }
            } else {
                if (stage.getWidth() > minWidth || mouseEventX + startX - stage.getWidth() > 0) {
                    setStageWidth(mouseEventX + startX);
                }
            }
        }

        /**
         * Used when mouse event is on the top or bottom
         */
        private void handleVerticalResize(MouseEvent mouseEvent, double mouseEventY) {
            double minHeight = stage.getMinHeight() > (border * 2) ? stage.getMinHeight() : (border * 2);
            if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.N_RESIZE.equals(cursorEvent)
                    || Cursor.NE_RESIZE.equals(cursorEvent)) {
                if (stage.getHeight() > minHeight || mouseEventY < 0) {
                    setStageHeight(stage.getY() - mouseEvent.getScreenY() + stage.getHeight());
                    stage.setY(mouseEvent.getScreenY());
                }
            } else {
                if (stage.getHeight() > minHeight || mouseEventY + startY - stage.getHeight() > 0) {
                    setStageHeight(mouseEventY + startY);
                }
            }
        }

        private Cursor getCursorEventType(double mouseEventX, double mouseEventY, double sceneWidth,
                                          double sceneHeight) {
            if (mouseEventX < border + CORNERDADIUS && mouseEventY < border + CORNERDADIUS) {
                return Cursor.NW_RESIZE;
            } else if (mouseEventX < border + CORNERDADIUS && mouseEventY > sceneHeight - border - CORNERDADIUS) {
                return Cursor.SW_RESIZE;
            } else if (mouseEventX > sceneWidth - border - CORNERDADIUS && mouseEventY < border + CORNERDADIUS) {
                return Cursor.NE_RESIZE;
            } else if (mouseEventX > sceneWidth - border - CORNERDADIUS
                    && mouseEventY > sceneHeight - border - CORNERDADIUS) {
                return Cursor.SE_RESIZE;
            } else if (mouseEventX < border + BORDERTHICKNESS) {
                return Cursor.W_RESIZE;
            } else if (mouseEventX > sceneWidth - border - BORDERTHICKNESS) {
                return Cursor.E_RESIZE;
            } else if (mouseEventY < border + BORDERTHICKNESS) {
                return Cursor.N_RESIZE;
            } else if (mouseEventY > sceneHeight - border - BORDERTHICKNESS) {
                return Cursor.S_RESIZE;
            } else {
                return Cursor.DEFAULT;
            }
        }

        private void setStageWidth(double width) {
            width = Math.min(width, maxWidth);
            width = Math.max(width, minWidth);
            stage.setWidth(width);
        }

        private void setStageHeight(double height) {
            height = Math.min(height, maxHeight);
            height = Math.max(height, minHeight);
            stage.setHeight(height);
        }

    }
}
