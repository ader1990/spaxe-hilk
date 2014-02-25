/*  1:   */ package sh.world;
/*  2:   */ 
/*  3:   */ public class TileBasedWorldController
/*  4:   */   extends WorldController
/*  5:   */ {
/*  6:   */   public TileBasedWorldController(WorldModel model, WorldView view)
/*  7:   */   {
/*  8:12 */     super(model, view);
/*  9:   */   }
/* 10:   */   
/* 11:   */   protected void toggleGrid()
/* 12:   */   {
/* 13:16 */     if ((this.view instanceof TileBasedWorldView)) {
/* 14:17 */       ((TileBasedWorldView)this.view).toggleGrid();
/* 15:   */     }
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.world.TileBasedWorldController
 * JD-Core Version:    0.7.0.1
 */