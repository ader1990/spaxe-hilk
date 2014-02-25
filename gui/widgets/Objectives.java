/*  1:   */ package sh.gui.widgets;
/*  2:   */ 
/*  3:   */ import de.matthiasmann.twl.DialogLayout;
/*  4:   */ import de.matthiasmann.twl.ResizableFrame;
/*  5:   */ import de.matthiasmann.twl.ScrollPane;
/*  6:   */ import de.matthiasmann.twl.TextArea;
/*  7:   */ import de.matthiasmann.twl.textarea.HTMLTextAreaModel;
/*  8:   */ import java.io.PrintStream;
/*  9:   */ import sh.SpaceHulkGameContainer;
/* 10:   */ 
/* 11:   */ public class Objectives
/* 12:   */   extends ResizableFrame
/* 13:   */ {
/* 14:   */   TextArea textArea;
/* 15:   */   ScrollPane scrollPane;
/* 16:   */   SpaceHulkGameContainer shgameContainer;
/* 17:   */   private StringBuilder sb;
/* 18:   */   private HTMLTextAreaModel textAreaModel;
/* 19:   */   
/* 20:   */   public Objectives(SpaceHulkGameContainer shgameContainer)
/* 21:   */   {
/* 22:23 */     this.shgameContainer = shgameContainer;
/* 23:   */     
/* 24:25 */     this.textAreaModel = new HTMLTextAreaModel();
/* 25:   */     
/* 26:27 */     this.textArea = new TextArea(this.textAreaModel);
/* 27:28 */     DialogLayout l = new DialogLayout();
/* 28:29 */     l.setTheme("content");
/* 29:30 */     this.scrollPane = new ScrollPane(this.textArea);
/* 30:31 */     l.add(this.scrollPane);
/* 31:32 */     add(l);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setObjective(String text)
/* 35:   */   {
/* 36:38 */     this.sb = new StringBuilder();
/* 37:39 */     this.sb.append("<div style=\"word-wrap: break-word;\">");
/* 38:40 */     this.sb.append(text);
/* 39:   */     
/* 40:42 */     this.sb.append("</div>");
/* 41:   */     
/* 42:44 */     boolean isAtEnd = this.scrollPane.getMaxScrollPosY() == this.scrollPane
/* 43:45 */       .getScrollPositionY();
/* 44:   */     
/* 45:47 */     this.textAreaModel.setHtml(this.sb.toString());
/* 46:49 */     if (isAtEnd)
/* 47:   */     {
/* 48:51 */       this.scrollPane.validateLayout();
/* 49:52 */       this.scrollPane.setScrollPositionY(this.scrollPane.getMaxScrollPosY());
/* 50:   */     }
/* 51:54 */     System.out.println(this.sb.toString());
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.widgets.Objectives
 * JD-Core Version:    0.7.0.1
 */