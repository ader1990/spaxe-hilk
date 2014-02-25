/*   1:    */ package sh.gui.TWL;
/*   2:    */ 
/*   3:    */ import de.matthiasmann.twl.GUI;
/*   4:    */ import org.newdawn.slick.Input;
/*   5:    */ import org.newdawn.slick.util.InputAdapter;
/*   6:    */ 
/*   7:    */ public class TWLInputForwarder
/*   8:    */   extends InputAdapter
/*   9:    */ {
/*  10:    */   private final Input input;
/*  11:    */   private final GUI gui;
/*  12:    */   private int mouseDown;
/*  13:    */   private boolean ignoreMouse;
/*  14:    */   private boolean lastPressConsumed;
/*  15:    */   
/*  16:    */   public TWLInputForwarder(GUI gui, Input input)
/*  17:    */   {
/*  18: 59 */     if (gui == null) {
/*  19: 60 */       throw new NullPointerException("gui");
/*  20:    */     }
/*  21: 62 */     if (input == null) {
/*  22: 63 */       throw new NullPointerException("input");
/*  23:    */     }
/*  24: 66 */     this.gui = gui;
/*  25: 67 */     this.input = input;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void mouseWheelMoved(int change)
/*  29:    */   {
/*  30: 72 */     if ((!this.ignoreMouse) && 
/*  31: 73 */       (this.gui.handleMouseWheel(change))) {
/*  32: 74 */       consume();
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void mousePressed(int button, int x, int y)
/*  37:    */   {
/*  38: 81 */     if (this.mouseDown == 0) {
/*  39: 83 */       this.lastPressConsumed = false;
/*  40:    */     }
/*  41: 86 */     this.mouseDown |= 1 << button;
/*  42: 88 */     if ((!this.ignoreMouse) && 
/*  43: 89 */       (this.gui.handleMouse(x, y, button, true)))
/*  44:    */     {
/*  45: 90 */       consume();
/*  46: 91 */       this.lastPressConsumed = true;
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void mouseReleased(int button, int x, int y)
/*  51:    */   {
/*  52: 98 */     this.mouseDown &= (1 << button ^ 0xFFFFFFFF);
/*  53:100 */     if (!this.ignoreMouse)
/*  54:    */     {
/*  55:101 */       if (this.gui.handleMouse(x, y, button, false)) {
/*  56:102 */         consume();
/*  57:    */       }
/*  58:    */     }
/*  59:104 */     else if (this.mouseDown == 0) {
/*  60:105 */       this.ignoreMouse = false;
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void mouseMoved(int oldX, int oldY, int newX, int newY)
/*  65:    */   {
/*  66:111 */     if ((this.mouseDown != 0) && (!this.lastPressConsumed))
/*  67:    */     {
/*  68:112 */       this.ignoreMouse = true;
/*  69:113 */       this.gui.clearMouseState();
/*  70:    */     }
/*  71:114 */     else if ((!this.ignoreMouse) && 
/*  72:115 */       (this.gui.handleMouse(newX, newY, -1, false)))
/*  73:    */     {
/*  74:116 */       consume();
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void mouseDragged(int oldx, int oldy, int newX, int newY)
/*  79:    */   {
/*  80:123 */     mouseMoved(oldx, oldy, newX, newY);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void keyPressed(int key, char c)
/*  84:    */   {
/*  85:128 */     if (this.gui.handleKey(key, c, true)) {
/*  86:129 */       consume();
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void keyReleased(int key, char c)
/*  91:    */   {
/*  92:135 */     if (this.gui.handleKey(key, c, false)) {
/*  93:136 */       consume();
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void mouseClicked(int button, int x, int y, int clickCount)
/*  98:    */   {
/*  99:142 */     if ((!this.ignoreMouse) && (this.lastPressConsumed) && (button == 0)) {
/* 100:143 */       consume();
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void consume()
/* 105:    */   {
/* 106:148 */     this.input.consumeEvent();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void inputStarted()
/* 110:    */   {
/* 111:153 */     this.gui.updateTime();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void inputEnded()
/* 115:    */   {
/* 116:158 */     this.gui.handleKeyRepeat();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void update()
/* 120:    */   {
/* 121:167 */     this.gui.setSize();
/* 122:168 */     this.gui.handleTooltips();
/* 123:169 */     this.gui.updateTimers();
/* 124:170 */     this.gui.invokeRunables();
/* 125:171 */     this.gui.validateLayout();
/* 126:172 */     this.gui.setCursor();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void render()
/* 130:    */   {
/* 131:181 */     this.gui.draw();
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.TWL.TWLInputForwarder
 * JD-Core Version:    0.7.0.1
 */