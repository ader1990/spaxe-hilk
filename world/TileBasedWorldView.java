/*  1:   */ package sh.world;
/*  2:   */ 
/*  3:   */ import java.util.Observable;
/*  4:   */ import org.newdawn.slick.Color;
/*  5:   */ import org.newdawn.slick.Graphics;
/*  6:   */ import org.newdawn.slick.SlickException;
/*  7:   */ import org.newdawn.slick.tiled.TiledMap;
/*  8:   */ 
/*  9:   */ public class TileBasedWorldView
/* 10:   */   extends WorldView
/* 11:   */ {
/* 12:19 */   private boolean showGrid = false;
/* 13:   */   protected TiledMap map;
/* 14:   */   
/* 15:   */   public TileBasedWorldView(WorldModel model, TiledMap map)
/* 16:   */   {
/* 17:23 */     super(model);
/* 18:   */     
/* 19:25 */     this.map = map;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void toggleGrid()
/* 23:   */   {
/* 24:29 */     this.showGrid = (!this.showGrid);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void draw(Graphics graphics)
/* 28:   */     throws SlickException
/* 29:   */   {
/* 30:34 */     for (int l = 0; l < this.map.getLayerCount(); l++) {
/* 31:35 */       if (Boolean.parseBoolean(this.map.getLayerProperty(l, "visible", "false"))) {
/* 32:37 */         this.map.render(0, 0, 0, 0, this.map.getWidth(), this.map.getHeight(), l, false);
/* 33:   */       }
/* 34:   */     }
/* 35:41 */     graphics.pushTransform();
/* 36:42 */     graphics.scale(getTileWidth(), getTileHeight());
/* 37:44 */     if (this.showGrid) {
/* 38:45 */       drawGrid(graphics);
/* 39:   */     }
/* 40:48 */     super.draw(graphics);
/* 41:49 */     graphics.popTransform();
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void drawGrid(Graphics graphics)
/* 45:   */   {
/* 46:53 */     if ((this.model instanceof TileBasedWorldModel))
/* 47:   */     {
/* 48:54 */       graphics.setColor(new Color(0.1F, 0.1F, 0.1F, 0.5F));
/* 49:56 */       for (int x = 0; x < ((TileBasedWorldModel)this.model).getWidthInTiles(); x++) {
/* 50:57 */         for (int y = 0; y < ((TileBasedWorldModel)this.model).getHeightInTiles(); y++) {
/* 51:58 */           graphics.drawRect(x, y, 1.0F, 1.0F);
/* 52:   */         }
/* 53:   */       }
/* 54:62 */       graphics.setColor(Color.white);
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int getTileWidth()
/* 59:   */   {
/* 60:67 */     return this.map.getTileWidth();
/* 61:   */   }
/* 62:   */   
/* 63:   */   public int getTileHeight()
/* 64:   */   {
/* 65:71 */     return this.map.getTileHeight();
/* 66:   */   }
/* 67:   */   
/* 68:   */   public int getWidth()
/* 69:   */   {
/* 70:75 */     return getTileWidth() * getModel().getWidthInTiles();
/* 71:   */   }
/* 72:   */   
/* 73:   */   public int getHeight()
/* 74:   */   {
/* 75:79 */     return getTileHeight() * getModel().getHeightInTiles();
/* 76:   */   }
/* 77:   */   
/* 78:   */   protected TileBasedWorldModel getModel()
/* 79:   */   {
/* 80:83 */     return (TileBasedWorldModel)this.model;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public void update(Observable observable, Object object) {}
/* 84:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.world.TileBasedWorldView
 * JD-Core Version:    0.7.0.1
 */