/*  1:   */ package sh.world.shworld;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.Color;
/*  4:   */ import org.newdawn.slick.Graphics;
/*  5:   */ import org.newdawn.slick.tiled.TiledMap;
/*  6:   */ import sh.world.TileBasedWorldView;
/*  7:   */ import sh.world.WorldModel;
/*  8:   */ 
/*  9:   */ public class SpaceHulkWorldView
/* 10:   */   extends TileBasedWorldView
/* 11:   */ {
/* 12:   */   public SpaceHulkWorldView(WorldModel model, TiledMap map)
/* 13:   */   {
/* 14:14 */     super(model, map);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void drawGrid(Graphics graphics)
/* 18:   */   {
/* 19:19 */     if ((this.model instanceof SpaceHulkWorldModel))
/* 20:   */     {
/* 21:41 */       graphics.setColor(new Color(0.1F, 0.1F, 0.1F, 0.5F));
/* 22:44 */       for (int x = 0; x < getModel().getWidthInTiles(); x++) {
/* 23:45 */         graphics.drawLine(x, 0.0F, x, getModel().getHeightInTiles());
/* 24:   */       }
/* 25:49 */       for (int y = 0; y < getModel().getHeightInTiles(); y++) {
/* 26:50 */         graphics.drawLine(0.0F, y, getModel().getWidthInTiles(), y);
/* 27:   */       }
/* 28:53 */       graphics.setColor(Color.white);
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected SpaceHulkWorldModel getModel()
/* 33:   */   {
/* 34:59 */     return (SpaceHulkWorldModel)this.model;
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.world.shworld.SpaceHulkWorldView
 * JD-Core Version:    0.7.0.1
 */