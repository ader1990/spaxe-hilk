/*   1:    */ package sh.gui.TWL;
/*   2:    */ 
/*   3:    */ import de.matthiasmann.twl.DesktopArea;
/*   4:    */ import de.matthiasmann.twl.Event;
/*   5:    */ import de.matthiasmann.twl.Widget;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ 
/*   8:    */ public class RootPane
/*   9:    */   extends DesktopArea
/*  10:    */ {
/*  11:    */   protected BasicTWLGameState state;
/*  12:    */   protected int oldMouseX;
/*  13:    */   protected int oldMouseY;
/*  14:    */   
/*  15:    */   public RootPane(BasicTWLGameState state)
/*  16:    */   {
/*  17: 49 */     if (state == null) {
/*  18: 50 */       throw new NullPointerException("state");
/*  19:    */     }
/*  20: 52 */     this.state = state;
/*  21:    */     
/*  22: 54 */     setCanAcceptKeyboardFocus(true);
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected RootPane()
/*  26:    */   {
/*  27: 62 */     this.state = null;
/*  28:    */     
/*  29: 64 */     setCanAcceptKeyboardFocus(true);
/*  30:    */     
/*  31: 66 */     System.err.println("This constructor is only intended to by called to preview subclass in the TWL Theme Editor");
/*  32:    */   }
/*  33:    */   
/*  34:    */   public final BasicTWLGameState getState()
/*  35:    */   {
/*  36: 75 */     return this.state;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public final boolean isPreviewMode()
/*  40:    */   {
/*  41: 83 */     return this.state == null;
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void keyboardFocusLost()
/*  45:    */   {
/*  46: 88 */     if (this.state != null) {
/*  47: 89 */       this.state.keyboardFocusLost();
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected boolean requestKeyboardFocus(Widget child)
/*  52:    */   {
/*  53: 95 */     if ((child != null) && (this.state != null)) {
/*  54: 96 */       this.state.keyboardFocusLost();
/*  55:    */     }
/*  56: 98 */     return super.requestKeyboardFocus(child);
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected boolean handleEvent(Event evt)
/*  60:    */   {
/*  61:103 */     if (super.handleEvent(evt)) {
/*  62:104 */       return true;
/*  63:    */     }
/*  64:107 */     if (this.state != null) {
/*  65:108 */       switch (evt.getType())
/*  66:    */       {
/*  67:    */       case MOUSE_MOVED: 
/*  68:110 */         this.state.keyPressed(evt.getKeyCode(), evt.getKeyChar());
/*  69:111 */         break;
/*  70:    */       case MOUSE_WHEEL: 
/*  71:113 */         this.state.keyReleased(evt.getKeyCode(), evt.getKeyChar());
/*  72:114 */         break;
/*  73:    */       case MOUSE_BTNDOWN: 
/*  74:116 */         this.state.mousePressed(evt.getMouseButton(), evt.getMouseX(), evt.getMouseY());
/*  75:117 */         break;
/*  76:    */       case MOUSE_BTNUP: 
/*  77:119 */         this.state.mouseReleased(evt.getMouseButton(), evt.getMouseX(), evt.getMouseY());
/*  78:120 */         break;
/*  79:    */       case MOUSE_CLICKED: 
/*  80:122 */         this.state.mouseClicked(evt.getMouseButton(), evt.getMouseX(), evt.getMouseY(), evt.getMouseClickCount());
/*  81:123 */         break;
/*  82:    */       case KEY_PRESSED: 
/*  83:    */       case KEY_RELEASED: 
/*  84:126 */         this.state.mouseMoved(this.oldMouseX, this.oldMouseY, evt.getMouseX(), evt.getMouseY());
/*  85:127 */         break;
/*  86:    */       case MOUSE_DRAGGED: 
/*  87:129 */         this.state.mouseDragged(this.oldMouseX, this.oldMouseY, evt.getMouseX(), evt.getMouseY());
/*  88:130 */         break;
/*  89:    */       case MOUSE_EXITED: 
/*  90:132 */         this.state.mouseWheelMoved(evt.getMouseWheelDelta());
/*  91:    */       }
/*  92:    */     }
/*  93:137 */     if (evt.isMouseEvent())
/*  94:    */     {
/*  95:138 */       this.oldMouseX = evt.getMouseX();
/*  96:139 */       this.oldMouseY = evt.getMouseY();
/*  97:    */     }
/*  98:141 */     return true;
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected void layout()
/* 102:    */   {
/* 103:146 */     super.layout();
/* 104:147 */     this.state.layoutRootPane();
/* 105:    */   }
/* 106:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.TWL.RootPane
 * JD-Core Version:    0.7.0.1
 */