/*   1:    */ package sh.states;
/*   2:    */ 
/*   3:    */ import de.matthiasmann.twl.Button;
/*   4:    */ import org.newdawn.slick.GameContainer;
/*   5:    */ import org.newdawn.slick.Graphics;
/*   6:    */ import org.newdawn.slick.Image;
/*   7:    */ import org.newdawn.slick.SlickException;
/*   8:    */ import org.newdawn.slick.state.StateBasedGame;
/*   9:    */ import sh.SpaceHulkGame;
/*  10:    */ import sh.SpaceHulkGameContainer;
/*  11:    */ import sh.gui.TWL.BasicTWLGameState;
/*  12:    */ import sh.gui.TWL.RootPane;
/*  13:    */ import sh.gui.widgets.MainMenu;
/*  14:    */ 
/*  15:    */ public class MainMenuState
/*  16:    */   extends BasicTWLGameState
/*  17:    */ {
/*  18: 24 */   int stateID = -1;
/*  19: 25 */   Image logo = null;
/*  20: 26 */   Image background = null;
/*  21:    */   Button startGame;
/*  22:    */   Button quit;
/*  23:    */   Button options;
/*  24:    */   MainMenu main;
/*  25:    */   SpaceHulkGame shgame;
/*  26:    */   SpaceHulkGameContainer shgameContainer;
/*  27:    */   
/*  28:    */   public MainMenuState(int stateID)
/*  29:    */   {
/*  30: 36 */     this.stateID = stateID;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getID()
/*  34:    */   {
/*  35: 42 */     return this.stateID;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void init(GameContainer arg0, StateBasedGame shgame)
/*  39:    */     throws SlickException
/*  40:    */   {
/*  41: 49 */     this.shgame = ((SpaceHulkGame)shgame);
/*  42:    */     
/*  43: 51 */     this.shgameContainer = ((SpaceHulkGameContainer)arg0);
/*  44: 52 */     this.logo = new Image("data/images/spacehilk2.png");
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected RootPane createRootPane()
/*  48:    */   {
/*  49: 60 */     RootPane rp = super.createRootPane();
/*  50: 61 */     rp.setSize(this.shgameContainer.getScreenWidth(), this.shgameContainer.getScreenHeight());
/*  51: 62 */     rp.setTheme("");
/*  52: 63 */     this.startGame = new Button("Start Game");
/*  53: 64 */     this.startGame.addCallback(new Runnable()
/*  54:    */     {
/*  55:    */       public void run()
/*  56:    */       {
/*  57: 69 */         MainMenuState.this.shgameContainer.playSound("click");
/*  58: 70 */         MainMenuState.this.shgame.enterState(4);
/*  59:    */       }
/*  60: 75 */     });
/*  61: 76 */     this.quit = new Button("Quit");
/*  62: 77 */     this.quit.addCallback(new Runnable()
/*  63:    */     {
/*  64:    */       public void run()
/*  65:    */       {
/*  66: 82 */         MainMenuState.this.shgameContainer.playSound("click");
/*  67: 83 */         System.exit(0);
/*  68:    */       }
/*  69: 86 */     });
/*  70: 87 */     this.options = new Button("Options");
/*  71: 88 */     this.options.addCallback(new Runnable()
/*  72:    */     {
/*  73:    */       public void run()
/*  74:    */       {
/*  75: 93 */         MainMenuState.this.shgameContainer.playSound("click");
/*  76: 94 */         MainMenuState.this.shgame.enterState(7);
/*  77:    */       }
/*  78: 98 */     });
/*  79: 99 */     this.main = new MainMenu(this.shgameContainer, this.startGame, this.quit, this.options);
/*  80:100 */     rp.add(this.main);
/*  81:    */     
/*  82:102 */     return rp;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void layoutRootPane()
/*  86:    */   {
/*  87:107 */     int middleX = this.shgameContainer.getWidth() / 2;
/*  88:108 */     int middleY = this.shgameContainer.getHeight() / 2;
/*  89:    */     
/*  90:110 */     int maxX = this.shgameContainer.getWidth();
/*  91:111 */     int maxY = this.shgameContainer.getHeight();
/*  92:    */     
/*  93:113 */     this.main.setSize(200, 300);
/*  94:114 */     this.main.setPosition(middleX - 100, middleY - 150);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
/*  98:    */     throws SlickException
/*  99:    */   {
/* 100:123 */     int middleX = this.shgameContainer.getWidth() / 2;
/* 101:124 */     this.logo.draw(middleX - this.logo.getWidth() / 2, 100.0F);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
/* 105:    */     throws SlickException
/* 106:    */   {}
/* 107:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.states.MainMenuState
 * JD-Core Version:    0.7.0.1
 */