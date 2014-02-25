/*  1:   */ package sh.world;
/*  2:   */ 
/*  3:   */ public class AbstractWorldFactory
/*  4:   */   implements WorldFactory
/*  5:   */ {
/*  6:   */   public WorldModel createWorldModel()
/*  7:   */   {
/*  8:16 */     return new WorldModel();
/*  9:   */   }
/* 10:   */   
/* 11:   */   public WorldView createWorldView(WorldModel model)
/* 12:   */   {
/* 13:21 */     return new WorldView(model);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public WorldController createWorldController(WorldModel model, WorldView view)
/* 17:   */   {
/* 18:26 */     return new WorldController(model, view);
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.world.AbstractWorldFactory
 * JD-Core Version:    0.7.0.1
 */