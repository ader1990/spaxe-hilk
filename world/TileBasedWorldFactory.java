/*  1:   */ package sh.world;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.tiled.TiledMap;
/*  4:   */ 
/*  5:   */ public class TileBasedWorldFactory
/*  6:   */   extends AbstractWorldFactory
/*  7:   */ {
/*  8:12 */   protected TiledMap map = null;
/*  9:   */   
/* 10:   */   public TileBasedWorldFactory(TiledMap map)
/* 11:   */   {
/* 12:15 */     this.map = map;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public WorldModel createWorldModel()
/* 16:   */   {
/* 17:20 */     return new TileBasedWorldModel(this.map);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public WorldView createWorldView(WorldModel model)
/* 21:   */   {
/* 22:25 */     return new TileBasedWorldView(model, this.map);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public WorldController createWorldController(WorldModel model, WorldView view)
/* 26:   */   {
/* 27:30 */     return new TileBasedWorldController(model, view);
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.world.TileBasedWorldFactory
 * JD-Core Version:    0.7.0.1
 */