/*  1:   */ package sh.states;
/*  2:   */ 
/*  3:   */ import de.matthiasmann.twl.Button;
/*  4:   */ import org.newdawn.slick.GameContainer;
/*  5:   */ import org.newdawn.slick.Graphics;
/*  6:   */ import org.newdawn.slick.SlickException;
/*  7:   */ import org.newdawn.slick.state.StateBasedGame;
/*  8:   */ import sh.SpaceHulkGame;
/*  9:   */ import sh.SpaceHulkGameContainer;
/* 10:   */ import sh.gui.TWL.BasicTWLGameState;
/* 11:   */ import sh.gui.TWL.RootPane;
/* 12:   */ import sh.gui.widgets.Options;
/* 13:   */ 
/* 14:   */ public class OptionsState
/* 15:   */   extends BasicTWLGameState
/* 16:   */ {
/* 17:26 */   int stateID = -1;
/* 18:   */   Button back;
/* 19:   */   Options main;
/* 20:   */   SpaceHulkGame shgame;
/* 21:   */   SpaceHulkGameContainer shgameContainer;
/* 22:   */   
/* 23:   */   public OptionsState(int stateID)
/* 24:   */   {
/* 25:35 */     this.stateID = stateID;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getID()
/* 29:   */   {
/* 30:41 */     return this.stateID;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void init(GameContainer arg0, StateBasedGame shgame)
/* 34:   */     throws SlickException
/* 35:   */   {
/* 36:48 */     this.shgame = ((SpaceHulkGame)shgame);
/* 37:   */     
/* 38:50 */     this.shgameContainer = ((SpaceHulkGameContainer)arg0);
/* 39:   */   }
/* 40:   */   
/* 41:   */   protected RootPane createRootPane()
/* 42:   */   {
/* 43:59 */     RootPane rp = super.createRootPane();
/* 44:60 */     rp.setSize(this.shgameContainer.getScreenWidth(), this.shgameContainer.getScreenHeight());
/* 45:61 */     rp.setTheme("");
/* 46:   */     
/* 47:   */ 
/* 48:   */ 
/* 49:65 */     this.main = new Options(this.shgameContainer, this.shgame);
/* 50:66 */     rp.add(this.main);
/* 51:   */     
/* 52:68 */     return rp;
/* 53:   */   }
/* 54:   */   
/* 55:   */   protected void layoutRootPane()
/* 56:   */   {
/* 57:73 */     int middleX = this.shgameContainer.getWidth() / 2;
/* 58:74 */     int middleY = this.shgameContainer.getHeight() / 2;
/* 59:   */     
/* 60:76 */     int maxX = this.shgameContainer.getWidth();
/* 61:77 */     int maxY = this.shgameContainer.getHeight();
/* 62:   */     
/* 63:79 */     this.main.setSize(middleX, middleY);
/* 64:80 */     this.main.setPosition(middleX / 2, middleY / 2);
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
/* 68:   */     throws SlickException
/* 69:   */   {}
/* 70:   */   
/* 71:   */   public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
/* 72:   */     throws SlickException
/* 73:   */   {}
/* 74:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.states.OptionsState
 * JD-Core Version:    0.7.0.1
 */