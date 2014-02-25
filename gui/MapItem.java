/*  1:   */ package sh.gui;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import org.newdawn.slick.Image;
/*  5:   */ import org.newdawn.slick.gui.GUIContext;
/*  6:   */ import org.newdawn.slick.gui.MouseOverArea;
/*  7:   */ 
/*  8:   */ public class MapItem
/*  9:   */   extends MouseOverArea
/* 10:   */ {
/* 11:   */   private String item;
/* 12:   */   
/* 13:   */   public MapItem(GUIContext container, Image image, int x, int y, String item)
/* 14:   */   {
/* 15: 9 */     super(container, image, x, y);
/* 16:10 */     this.item = item;
/* 17:11 */     System.out.println(x + " " + y + " " + image.getHeight() + " " + image.getWidth());
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getItem()
/* 21:   */   {
/* 22:16 */     return this.item;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void mouseClicked(int button, int x, int y, int clickCount)
/* 26:   */   {
/* 27:24 */     System.out.println("MOUSE CLICKED ON BUTTON");
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.MapItem
 * JD-Core Version:    0.7.0.1
 */