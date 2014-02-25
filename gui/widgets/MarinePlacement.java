/*  1:   */ package sh.gui.widgets;
/*  2:   */ 
/*  3:   */ import de.matthiasmann.twl.Label;
/*  4:   */ import de.matthiasmann.twl.ListBox;
/*  5:   */ import de.matthiasmann.twl.ResizableFrame;
/*  6:   */ import de.matthiasmann.twl.model.SimpleChangableListModel;
/*  7:   */ import sh.SpaceHulkGameContainer;
/*  8:   */ 
/*  9:   */ public class MarinePlacement
/* 10:   */   extends ResizableFrame
/* 11:   */ {
/* 12:   */   SpaceHulkGameContainer shgameContainer;
/* 13:   */   Label marineName;
/* 14:   */   Label cost;
/* 15:   */   Label description;
/* 16:   */   Label pointsLeft;
/* 17:   */   ListBox<String> marineTypeList;
/* 18:   */   
/* 19:   */   public MarinePlacement(SpaceHulkGameContainer shgameContainer, ListBox<String> marineTypeList, Label marineName, Label cost, Label description, Label pointsLeft)
/* 20:   */   {
/* 21:29 */     this.shgameContainer = shgameContainer;
/* 22:30 */     this.marineName = marineName;
/* 23:31 */     this.marineTypeList = marineTypeList;
/* 24:32 */     this.cost = cost;
/* 25:33 */     this.description = description;
/* 26:34 */     this.pointsLeft = pointsLeft;
/* 27:35 */     SimpleChangableListModel<String> marineTypeListModel = new SimpleChangableListModel();
/* 28:36 */     marineTypeListModel.addElement("Marine");
/* 29:37 */     marineTypeListModel.addElement("Flamer");
/* 30:38 */     marineTypeListModel.addElement("Sergeant");
/* 31:39 */     marineTypeList.setModel(marineTypeListModel);
/* 32:40 */     add(pointsLeft);
/* 33:41 */     add(cost);
/* 34:42 */     add(description);
/* 35:43 */     add(marineName);
/* 36:44 */     add(marineTypeList);
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected void layout()
/* 40:   */   {
/* 41:50 */     int middleX = this.shgameContainer.getWidth() / 2;
/* 42:51 */     int middleY = this.shgameContainer.getHeight() / 2;
/* 43:   */     
/* 44:53 */     int maxX = this.shgameContainer.getWidth();
/* 45:54 */     int maxY = this.shgameContainer.getHeight();
/* 46:   */     
/* 47:56 */     this.pointsLeft.setPosition(maxX - maxX / 10 + 15, 20);
/* 48:57 */     this.pointsLeft.setSize(getWidth() - 15, 20);
/* 49:   */     
/* 50:59 */     this.marineTypeList.setPosition(maxX - maxX / 10 + 15, 45);
/* 51:60 */     this.marineTypeList.setSize(getWidth() - 30, 200);
/* 52:   */     
/* 53:62 */     this.marineName.setPosition(maxX - maxX / 10 + 15, 255);
/* 54:63 */     this.marineName.setSize(getWidth() - 15, 20);
/* 55:   */     
/* 56:65 */     this.cost.setPosition(maxX - maxX / 10 + 15, 285);
/* 57:66 */     this.cost.setSize(getWidth() - 15, 20);
/* 58:   */     
/* 59:68 */     this.description.setPosition(maxX - maxX / 10 + 15, 315);
/* 60:69 */     this.description.setSize(getWidth() - 15, 40);
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.widgets.MarinePlacement
 * JD-Core Version:    0.7.0.1
 */