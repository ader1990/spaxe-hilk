/*  1:   */ package sh.gui.widgets;
/*  2:   */ 
/*  3:   */ import de.matthiasmann.twl.Button;
/*  4:   */ import de.matthiasmann.twl.ResizableFrame;
/*  5:   */ import de.matthiasmann.twl.ResizableFrame.ResizableAxis;
/*  6:   */ import sh.SpaceHulkGameContainer;
/*  7:   */ 
/*  8:   */ public class MainMenu
/*  9:   */   extends ResizableFrame
/* 10:   */ {
/* 11:   */   Button startGame;
/* 12:   */   Button options;
/* 13:   */   Button quit;
/* 14:   */   SpaceHulkGameContainer shgameContainer;
/* 15:   */   
/* 16:   */   public MainMenu(SpaceHulkGameContainer shgameContainer, Button startGame, Button quit, Button options)
/* 17:   */   {
/* 18:21 */     setResizableAxis(ResizableFrame.ResizableAxis.NONE);
/* 19:22 */     setTheme("infobox");
/* 20:23 */     this.shgameContainer = shgameContainer;
/* 21:24 */     this.startGame = startGame;
/* 22:25 */     this.options = options;
/* 23:26 */     this.quit = quit;
/* 24:   */     
/* 25:28 */     add(startGame);
/* 26:29 */     add(options);
/* 27:30 */     add(quit);
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected void layout()
/* 31:   */   {
/* 32:40 */     int middleX = this.shgameContainer.getWidth() / 2;
/* 33:41 */     int middleY = this.shgameContainer.getHeight() / 2;
/* 34:   */     
/* 35:43 */     int maxX = this.shgameContainer.getWidth();
/* 36:44 */     int maxY = this.shgameContainer.getHeight();
/* 37:   */     
/* 38:46 */     this.startGame.setPosition(middleX - 50, middleY - 40);
/* 39:47 */     this.startGame.setSize(100, 20);
/* 40:   */     
/* 41:49 */     this.options.setPosition(middleX - 50, middleY - 10);
/* 42:50 */     this.options.setSize(100, 20);
/* 43:   */     
/* 44:52 */     this.quit.setPosition(middleX - 50, middleY + 20);
/* 45:53 */     this.quit.setSize(100, 20);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.widgets.MainMenu
 * JD-Core Version:    0.7.0.1
 */