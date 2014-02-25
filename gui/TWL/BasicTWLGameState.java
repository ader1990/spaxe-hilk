/*   1:    */ package sh.gui.TWL;
/*   2:    */ 
/*   3:    */ import de.matthiasmann.twl.ActionMap;
/*   4:    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*   5:    */ import org.newdawn.slick.GameContainer;
/*   6:    */ import org.newdawn.slick.SlickException;
/*   7:    */ import org.newdawn.slick.state.BasicGameState;
/*   8:    */ import org.newdawn.slick.state.StateBasedGame;
/*   9:    */ import sh.multiplayer.ChatMessage;
/*  10:    */ 
/*  11:    */ public abstract class BasicTWLGameState
/*  12:    */   extends BasicGameState
/*  13:    */ {
/*  14:    */   private RootPane rootPane;
/*  15: 57 */   protected ConcurrentLinkedQueue<ChatMessage> incomingChatList = new ConcurrentLinkedQueue();
/*  16:    */   
/*  17:    */   public RootPane getRootPane()
/*  18:    */   {
/*  19: 65 */     if (this.rootPane == null)
/*  20:    */     {
/*  21: 66 */       this.rootPane = createRootPane();
/*  22: 67 */       if (this.rootPane.getState() != this) {
/*  23: 68 */         throw new IllegalStateException("rootPane.getState() != this");
/*  24:    */       }
/*  25:    */     }
/*  26: 71 */     return this.rootPane;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void enter(GameContainer container, StateBasedGame game)
/*  30:    */     throws SlickException
/*  31:    */   {
/*  32: 85 */     ((TWLStateBasedGame)game).setRootPane(getRootPane());
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected RootPane createRootPane()
/*  36:    */   {
/*  37:106 */     assert (this.rootPane == null) : "RootPane already created";
/*  38:    */     
/*  39:108 */     RootPane rp = new RootPane(this);
/*  40:109 */     rp.setTheme("state" + getID());
/*  41:110 */     rp.getOrCreateActionMap().addMapping(this);
/*  42:111 */     return rp;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected void keyboardFocusLost() {}
/*  46:    */   
/*  47:    */   protected void layoutRootPane() {}
/*  48:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.TWL.BasicTWLGameState
 * JD-Core Version:    0.7.0.1
 */