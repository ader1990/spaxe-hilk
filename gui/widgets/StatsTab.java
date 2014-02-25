/*  1:   */ package sh.gui.widgets;
/*  2:   */ 
/*  3:   */ import de.matthiasmann.twl.Graph;
/*  4:   */ import de.matthiasmann.twl.Widget;
/*  5:   */ import de.matthiasmann.twl.model.GraphLineModel;
/*  6:   */ import de.matthiasmann.twl.model.SimpleGraphLineModel;
/*  7:   */ import de.matthiasmann.twl.model.SimpleGraphModel;
/*  8:   */ import sh.SpaceHulkGameContainer;
/*  9:   */ 
/* 10:   */ public class StatsTab
/* 11:   */   extends Widget
/* 12:   */ {
/* 13:   */   SpaceHulkGameContainer shgameContainer;
/* 14:   */   Graph graph;
/* 15:   */   SimpleGraphLineModel model;
/* 16:   */   
/* 17:   */   public StatsTab(SpaceHulkGameContainer shgameContainer, Graph graph)
/* 18:   */   {
/* 19:21 */     this.graph = graph;
/* 20:22 */     this.shgameContainer = shgameContainer;
/* 21:23 */     this.model = new SimpleGraphLineModel("default", 500, 0.0F, 30.0F);
/* 22:   */     
/* 23:25 */     graph = new Graph(new SimpleGraphModel(new GraphLineModel[] { this.model }));
/* 24:26 */     graph.setTheme("/graph");
/* 25:27 */     add(graph);
/* 26:28 */     this.model.addPoint(10.0F);
/* 27:29 */     this.model.addPoint(10.0F);
/* 28:30 */     this.model.addPoint(10.0F);
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected void layout()
/* 32:   */   {
/* 33:40 */     int middleX = this.shgameContainer.getWidth() / 2;
/* 34:41 */     int middleY = this.shgameContainer.getHeight() / 2;
/* 35:   */     
/* 36:43 */     int maxX = this.shgameContainer.getWidth();
/* 37:44 */     int maxY = this.shgameContainer.getHeight();
/* 38:   */     
/* 39:46 */     this.graph.setPosition(0, 100);
/* 40:47 */     this.graph.setSize(maxX, maxY - 100);
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.widgets.StatsTab
 * JD-Core Version:    0.7.0.1
 */