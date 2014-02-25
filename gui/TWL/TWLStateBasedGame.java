/*   1:    */ package sh.gui.TWL;
/*   2:    */ 
/*   3:    */ import de.matthiasmann.twl.GUI;
/*   4:    */ import de.matthiasmann.twl.Widget;
/*   5:    */ import de.matthiasmann.twl.renderer.Renderer;
/*   6:    */ import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
/*   7:    */ import de.matthiasmann.twl.theme.ThemeManager;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.net.URL;
/*  10:    */ import org.lwjgl.opengl.GL11;
/*  11:    */ import org.newdawn.slick.GameContainer;
/*  12:    */ import org.newdawn.slick.Graphics;
/*  13:    */ import org.newdawn.slick.Input;
/*  14:    */ import org.newdawn.slick.SlickException;
/*  15:    */ import org.newdawn.slick.state.GameState;
/*  16:    */ import org.newdawn.slick.state.StateBasedGame;
/*  17:    */ import org.newdawn.slick.state.transition.Transition;
/*  18:    */ 
/*  19:    */ public abstract class TWLStateBasedGame
/*  20:    */   extends StateBasedGame
/*  21:    */ {
/*  22:    */   private final Widget emptyRootWidget;
/*  23:    */   private GUI gui;
/*  24:    */   private boolean guiInitialized;
/*  25:    */   
/*  26:    */   protected TWLStateBasedGame(String name)
/*  27:    */   {
/*  28: 61 */     super(name);
/*  29:    */     
/*  30: 63 */     this.emptyRootWidget = new Widget();
/*  31: 64 */     this.emptyRootWidget.setTheme("");
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void addState(BasicTWLGameState state)
/*  35:    */   {
/*  36: 73 */     super.addState(state);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void addState(GameState state)
/*  40:    */   {
/*  41: 86 */     if (!(state instanceof BasicTWLGameState)) {
/*  42: 87 */       throw new IllegalArgumentException("state must be a BasicTWLGameState");
/*  43:    */     }
/*  44: 89 */     super.addState(state);
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected abstract URL getThemeURL();
/*  48:    */   
/*  49:    */   public void enterState(int id, Transition leave, Transition enter)
/*  50:    */   {
/*  51:110 */     if (this.gui != null) {
/*  52:111 */       this.gui.setRootPane(this.emptyRootWidget);
/*  53:    */     }
/*  54:113 */     super.enterState(id, leave, enter);
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected ThemeManager loadTheme(Renderer renderer)
/*  58:    */     throws IOException
/*  59:    */   {
/*  60:117 */     URL url = getThemeURL();
/*  61:118 */     assert (url != null);
/*  62:119 */     return ThemeManager.createThemeManager(url, renderer);
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void setRootPane(RootPane rootPane)
/*  66:    */     throws SlickException
/*  67:    */   {
/*  68:123 */     if (!this.guiInitialized)
/*  69:    */     {
/*  70:124 */       this.guiInitialized = true;
/*  71:125 */       initGUI();
/*  72:    */     }
/*  73:127 */     if (this.gui != null) {
/*  74:128 */       this.gui.setRootPane(rootPane);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void initGUI()
/*  79:    */     throws SlickException
/*  80:    */   {
/*  81:133 */     GL11.glPushAttrib(1048575);
/*  82:    */     try
/*  83:    */     {
/*  84:135 */       Renderer renderer = new LWJGLRenderer();
/*  85:136 */       ThemeManager theme = loadTheme(renderer);
/*  86:    */       
/*  87:138 */       this.gui = new GUI(this.emptyRootWidget, renderer, null);
/*  88:139 */       this.gui.applyTheme(theme);
/*  89:    */       
/*  90:141 */       Input input = getContainer().getInput();
/*  91:142 */       TWLInputForwarder inputForwarder = new TWLInputForwarder(this.gui, input);
/*  92:143 */       input.addPrimaryListener(inputForwarder);
/*  93:    */     }
/*  94:    */     catch (Throwable e)
/*  95:    */     {
/*  96:145 */       throw new SlickException("Could not initialize TWL GUI", e);
/*  97:    */     }
/*  98:    */     finally
/*  99:    */     {
/* 100:147 */       GL11.glPopAttrib();
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected void postRenderState(GameContainer container, Graphics g)
/* 105:    */     throws SlickException
/* 106:    */   {
/* 107:153 */     if (this.gui != null) {
/* 108:154 */       this.gui.draw();
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected void postUpdateState(GameContainer container, int delta)
/* 113:    */     throws SlickException
/* 114:    */   {
/* 115:160 */     if (this.gui != null)
/* 116:    */     {
/* 117:161 */       this.gui.setSize();
/* 118:162 */       this.gui.handleTooltips();
/* 119:163 */       this.gui.updateTimers();
/* 120:164 */       this.gui.invokeRunables();
/* 121:165 */       this.gui.validateLayout();
/* 122:166 */       this.gui.setCursor();
/* 123:    */     }
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.TWL.TWLStateBasedGame
 * JD-Core Version:    0.7.0.1
 */