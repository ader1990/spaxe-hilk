/*  1:   */ package sh.world.shworld;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.tiled.TiledMap;
/*  4:   */ import sh.world.TileBasedWorldFactory;
/*  5:   */ import sh.world.WorldController;
/*  6:   */ import sh.world.WorldModel;
/*  7:   */ import sh.world.WorldView;
/*  8:   */ 
/*  9:   */ public class SpaceHulkWorldFactory
/* 10:   */   extends TileBasedWorldFactory
/* 11:   */ {
/* 12:12 */   int players = 0;
/* 13:   */   
/* 14:   */   public SpaceHulkWorldFactory(TiledMap map, int players)
/* 15:   */   {
/* 16:14 */     super(map);
/* 17:15 */     this.players = players;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public WorldModel createWorldModel()
/* 21:   */   {
/* 22:20 */     return new SpaceHulkWorldModel(this.map, this.players);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public WorldView createWorldView(WorldModel model)
/* 26:   */   {
/* 27:25 */     return new SpaceHulkWorldView(model, this.map);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public WorldController createWorldController(WorldModel model, WorldView view)
/* 31:   */   {
/* 32:30 */     return new SpaceHulkWorldController(model, view);
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.world.shworld.SpaceHulkWorldFactory
 * JD-Core Version:    0.7.0.1
 */