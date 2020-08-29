package yadokaris_Status_HUD_plus;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.InvalidDnDOperationException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

class DnDList<E> extends JList<E> implements DragGestureListener, Transferable {
	  private static final Color LINE_COLOR = new Color(0x64_64_FF);
	  private static final String NAME = "test";
	  private static final DataFlavor FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, NAME);
	  private static final Color EVEN_BACKGROUND = new Color(0xF0_F0_F0);
	  private final Rectangle targetLine = new Rectangle();
	  protected int draggedIndex = -1;
	  protected int targetIndex = -1;

	  protected DnDList() {
	    super();
	    new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new CDropTargetListener(), true);
	    DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(
	        (Component) this, DnDConstants.ACTION_COPY_OR_MOVE, (DragGestureListener) this);
	  }

	  @Override public void updateUI() {
	    setCellRenderer(null);
	    super.updateUI();
	    ListCellRenderer<? super E> renderer = getCellRenderer();
	    setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
	      Component c = renderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	      if (isSelected) {
	        c.setForeground(list.getSelectionForeground());
	        c.setBackground(list.getSelectionBackground());
	      } else {
	        c.setForeground(list.getForeground());
	        c.setBackground(index % 2 == 0 ? EVEN_BACKGROUND : list.getBackground());
	      }
	      return c;
	    });
	  }

	  @Override protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (targetIndex >= 0) {
	      Graphics2D g2 = (Graphics2D) g.create();
	      g2.setPaint(LINE_COLOR);
	      g2.fill(targetLine);
	      g2.dispose();
	    }
	  }

	  protected void initTargetLine(Point p) {
	    Rectangle rect = getCellBounds(0, 0);
	    int cellHeight = rect.height;
	    int lineHeight = 2;
	    int modelSize = getModel().getSize();
	    targetIndex = -1;
	    targetLine.setSize(rect.width, lineHeight);
	    for (int i = 0; i < modelSize; i++) {
	      rect.setLocation(0, cellHeight * i - cellHeight / 2);
	      if (rect.contains(p)) {
	        targetIndex = i;
	        targetLine.setLocation(0, i * cellHeight);
	        break;
	      }
	    }
	    if (targetIndex < 0) {
	      targetIndex = modelSize;
	      targetLine.setLocation(0, targetIndex * cellHeight - lineHeight);
	    }
	  }

	  // Interface: DragGestureListener
	  @Override public void dragGestureRecognized(DragGestureEvent e) {
	    boolean isMoreThanOneItemSelected = getSelectedIndices().length > 1;
	    if (isMoreThanOneItemSelected) {
	      return;
	    }
	    draggedIndex = locationToIndex(e.getDragOrigin());
	    if (draggedIndex < 0) {
	      return;
	    }
	    try {
	      e.startDrag(DragSource.DefaultMoveDrop, (Transferable) this, new ListDragSourceListener());
	    } catch (InvalidDnDOperationException ex) {
	      throw new IllegalStateException(ex);
	    }
	  }

	  // Interface: Transferable
	  @Override public Object getTransferData(DataFlavor flavor) {
	    return this;
	  }

	  @Override public DataFlavor[] getTransferDataFlavors() {
	    return new DataFlavor[] {FLAVOR};
	  }

	  @Override public boolean isDataFlavorSupported(DataFlavor flavor) {
	    return flavor.getHumanPresentableName().equals(NAME);
	  }

	  private class CDropTargetListener implements DropTargetListener {
	    // DropTargetListener interface
	    @Override public void dragExit(DropTargetEvent e) {
	      targetIndex = -1;
	      repaint();
	    }

	    @Override public void dragEnter(DropTargetDragEvent e) {
	      if (isDragAcceptable(e)) {
	        e.acceptDrag(e.getDropAction());
	      } else {
	        e.rejectDrag();
	      }
	    }

	    @Override public void dragOver(DropTargetDragEvent e) {
	      if (isDragAcceptable(e)) {
	        e.acceptDrag(e.getDropAction());
	      } else {
	        e.rejectDrag();
	        return;
	      }
	      initTargetLine(e.getLocation());
	      repaint();
	    }

	    @Override public void dropActionChanged(DropTargetDragEvent e) {
	      // if (isDragAcceptable(e)) {
	      //   e.acceptDrag(e.getDropAction());
	      // } else {
	      //   e.rejectDrag();
	      // }
	    }

	    @Override public void drop(DropTargetDropEvent e) {
	      DefaultListModel<E> model = (DefaultListModel<E>) getModel();
	      // Transferable t = e.getTransferable();
	      // DataFlavor[] f = t.getTransferDataFlavors();
	      // try {
	      //   Component comp = (Component) t.getTransferData(f[0]);
	      // } catch (UnsupportedFlavorException | IOException ex) {
	      //   e.dropComplete(false);
	      // }
	      if (isDropAcceptable(e) && targetIndex >= 0) {
	        E str = model.get(draggedIndex);
	        if (targetIndex == draggedIndex) {
	          setSelectedIndex(targetIndex);
	        } else if (targetIndex < draggedIndex) {
	          model.remove(draggedIndex);
	          model.add(targetIndex, str);
	          setSelectedIndex(targetIndex);
	        } else {
	          model.add(targetIndex, str);
	          model.remove(draggedIndex);
	          setSelectedIndex(targetIndex - 1);
	        }
	        e.dropComplete(true);
	      } else {
	        e.dropComplete(false);
	      }
	      e.dropComplete(false);
	      targetIndex = -1;
	      repaint();
	    }

	    private boolean isDragAcceptable(DropTargetDragEvent e) {
	      return isDataFlavorSupported(e.getCurrentDataFlavors()[0]);
	    }

	    private boolean isDropAcceptable(DropTargetDropEvent e) {
	      return isDataFlavorSupported(e.getTransferable().getTransferDataFlavors()[0]);
	    }
	  }
	}

	class ListDragSourceListener implements DragSourceListener {
	  @Override public void dragEnter(DragSourceDragEvent e) {
	    e.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
	  }

	  @Override public void dragExit(DragSourceEvent e) {
	    e.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
	  }

	  @Override public void dragOver(DragSourceDragEvent e) {
	    /* not needed */
	  }

	  @Override public void dropActionChanged(DragSourceDragEvent e) {
	    /* not needed */
	  }

	  @Override public void dragDropEnd(DragSourceDropEvent e) {
	    /* not needed */
	  }

}
