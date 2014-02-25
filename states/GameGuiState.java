/*  1:   */ package sh.states;
/*  2:   */ 
/*  3:   */ import de.matthiasmann.twl.Label;
/*  4:   */ import org.newdawn.slick.GameContainer;
/*  5:   */ import org.newdawn.slick.Input;
/*  6:   */ import org.newdawn.slick.SlickException;
/*  7:   */ import org.newdawn.slick.state.StateBasedGame;
/*  8:   */ import sh.SpaceHulkGameContainer;
/*  9:   */ import sh.gui.TWL.RootPane;
/* 10:   */ import sh.gui.widgets.ChatBox;
/* 11:   */ 
/* 12:   */ public class GameGuiState
/* 13:   */   extends GameState
/* 14:   */ {
/* 15:   */   Label feedbackText;
/* 16:   */   Label apLabel;
/* 17:   */   protected ChatBox chatbox;
/* 18:35 */   private boolean showChat = true;
/* 19:36 */   protected boolean chatting = false;
/* 20:   */   
/* 21:   */   public GameGuiState(int stateID)
/* 22:   */   {
/* 23:39 */     super(stateID);
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected RootPane createRootPane()
/* 27:   */   {
/* 28:48 */     RootPane rp = super.createRootPane();
/* 29:49 */     rp.setSize(this.shgameContainer.getScreenWidth(), this.shgameContainer.getScreenHeight());
/* 30:50 */     rp.setTheme("");
/* 31:51 */     this.feedbackText = new Label();
/* 32:   */     
/* 33:53 */     this.apLabel = new Label();
/* 34:54 */     this.apLabel.setText("AP");
/* 35:55 */     this.chatbox = new ChatBox(this.shgameContainer);
/* 36:56 */     rp.add(this.chatbox);
/* 37:57 */     rp.add(this.feedbackText);
/* 38:58 */     return rp;
/* 39:   */   }
/* 40:   */   
/* 41:   */   protected void layoutRootPane()
/* 42:   */   {
/* 43:63 */     int middleX = this.shgameContainer.getWidth() / 2;
/* 44:64 */     int middleY = this.shgameContainer.getHeight() / 2;
/* 45:   */     
/* 46:66 */     int maxX = this.shgameContainer.getWidth();
/* 47:67 */     int maxY = this.shgameContainer.getHeight();
/* 48:   */     
/* 49:69 */     this.feedbackText.setPosition(middleY - 100, 10);
/* 50:70 */     this.feedbackText.setSize(200, 20);
/* 51:71 */     this.chatbox.setPosition(maxX / 10, maxY - 100);
/* 52:72 */     this.chatbox.setSize(maxX - maxX / 5, 100);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void update(GameContainer container, StateBasedGame arg1, int arg2)
/* 56:   */     throws SlickException
/* 57:   */   {
/* 58:82 */     super.update(container, arg1, arg2);
/* 59:84 */     if (container.getInput().isKeyPressed(15))
/* 60:   */     {
/* 61:86 */       this.showChat = (!this.showChat);
/* 62:87 */       this.chatbox.setVisible(this.showChat);
/* 63:   */     }
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.states.GameGuiState
 * JD-Core Version:    0.7.0.1
 */