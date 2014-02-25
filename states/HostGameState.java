/*   1:    */ package sh.states;
/*   2:    */ 
/*   3:    */ import de.matthiasmann.twl.Button;
/*   4:    */ import de.matthiasmann.twl.CallbackWithReason;
/*   5:    */ import de.matthiasmann.twl.Label;
/*   6:    */ import de.matthiasmann.twl.ListBox;
/*   7:    */ import de.matthiasmann.twl.ListBox.CallbackReason;
/*   8:    */ import de.matthiasmann.twl.TextArea;
/*   9:    */ import de.matthiasmann.twl.model.SimpleChangableListModel;
/*  10:    */ import de.matthiasmann.twl.textarea.SimpleTextAreaModel;
/*  11:    */ import java.io.File;
/*  12:    */ import java.io.FilenameFilter;
/*  13:    */ import org.newdawn.slick.GameContainer;
/*  14:    */ import org.newdawn.slick.Graphics;
/*  15:    */ import org.newdawn.slick.SlickException;
/*  16:    */ import org.newdawn.slick.state.StateBasedGame;
/*  17:    */ import org.newdawn.slick.tiled.TiledMap;
/*  18:    */ import sh.SpaceHulkGameContainer;
/*  19:    */ import sh.gui.TWL.BasicTWLGameState;
/*  20:    */ import sh.gui.TWL.RootPane;
/*  21:    */ import sh.utils.OnlyExt;
/*  22:    */ 
/*  23:    */ public class HostGameState
/*  24:    */   extends BasicTWLGameState
/*  25:    */ {
/*  26: 36 */   private int stateID = -1;
/*  27:    */   SpaceHulkGameContainer container;
/*  28:    */   StateBasedGame sb;
/*  29:    */   Label mapSelection;
/*  30:    */   Button startGame;
/*  31:    */   Label MapName;
/*  32:    */   Label Players;
/*  33:    */   ListBox<String> mapList;
/*  34:    */   Button back;
/*  35:    */   TextArea description;
/*  36:    */   
/*  37:    */   public HostGameState(int stateID)
/*  38:    */   {
/*  39: 49 */     this.stateID = stateID;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void init(GameContainer cont, StateBasedGame sb)
/*  43:    */     throws SlickException
/*  44:    */   {
/*  45: 55 */     this.container = ((SpaceHulkGameContainer)cont);
/*  46: 56 */     this.sb = sb;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected RootPane createRootPane()
/*  50:    */   {
/*  51: 63 */     RootPane rp = super.createRootPane();
/*  52: 64 */     rp.setSize(this.container.getScreenWidth(), this.container.getScreenHeight());
/*  53: 65 */     rp.setTheme("");
/*  54: 66 */     File dir = new File("data/maps");
/*  55: 67 */     this.back = new Button("Back");
/*  56: 68 */     this.back.addCallback(new Runnable()
/*  57:    */     {
/*  58:    */       public void run()
/*  59:    */       {
/*  60: 72 */         HostGameState.this.container.playSound("click");
/*  61: 73 */         HostGameState.this.sb.enterState(4);
/*  62:    */       }
/*  63: 77 */     });
/*  64: 78 */     this.mapList = new ListBox();
/*  65: 79 */     this.MapName = new Label();
/*  66: 80 */     this.Players = new Label();
/*  67: 81 */     this.description = new TextArea();
/*  68: 82 */     this.mapSelection = new Label();
/*  69: 83 */     this.mapSelection.setText("Map selection");
/*  70: 84 */     SimpleChangableListModel<String> model = new SimpleChangableListModel();
/*  71: 85 */     FilenameFilter filter = new OnlyExt("tmx");
/*  72: 86 */     final String[] list = dir.list(filter);
/*  73: 87 */     model.addElements(list);
/*  74:    */     
/*  75: 89 */     this.mapList = new ListBox(model);
/*  76: 90 */     this.mapList.setTheme("listbox");
/*  77: 91 */     this.mapList.addCallback(new CallbackWithReason()
/*  78:    */     {
/*  79:    */       public void callback(ListBox.CallbackReason reason)
/*  80:    */       {
/*  81:    */         try
/*  82:    */         {
/*  83: 98 */           int index = HostGameState.this.mapList.getSelected();
/*  84:100 */           if ((index >= 0) && (index < list.length))
/*  85:    */           {
/*  86:102 */             TiledMap map = new TiledMap("data/maps/" + list[index]);
/*  87:    */             
/*  88:104 */             String mapName = map.getMapProperty("name", "No name");
/*  89:    */             
/*  90:106 */             String players = map.getMapProperty("players", "2");
/*  91:107 */             SimpleTextAreaModel textModel = new SimpleTextAreaModel();
/*  92:108 */             textModel.setText(map.getMapProperty("description", "No description"));
/*  93:    */             
/*  94:110 */             HostGameState.this.MapName.setText("Map name: " + mapName);
/*  95:111 */             HostGameState.this.Players.setText("Players: " + players);
/*  96:    */             
/*  97:    */ 
/*  98:114 */             HostGameState.this.description.setModel(textModel);
/*  99:    */           }
/* 100:    */         }
/* 101:    */         catch (Exception localException) {}
/* 102:    */       }
/* 103:127 */     });
/* 104:128 */     this.startGame = new Button("Start game");
/* 105:129 */     this.startGame.setTheme("button");
/* 106:130 */     this.startGame.addCallback(new Runnable()
/* 107:    */     {
/* 108:    */       public void run()
/* 109:    */       {
/* 110:134 */         int index = HostGameState.this.mapList.getSelected();
/* 111:135 */         if ((index >= 0) && (index < list.length))
/* 112:    */         {
/* 113:137 */           HostGameState.this.container.playSound("click");
/* 114:138 */           HostGameState.this.container.setMap2("data/maps/" + list[index]);
/* 115:139 */           HostGameState.this.container.StartServer();
/* 116:140 */           HostGameState.this.sb.enterState(1);
/* 117:    */         }
/* 118:    */       }
/* 119:144 */     });
/* 120:145 */     rp.add(this.mapSelection);
/* 121:146 */     rp.add(this.description);
/* 122:147 */     rp.add(this.Players);
/* 123:148 */     rp.add(this.MapName);
/* 124:149 */     rp.add(this.mapList);
/* 125:150 */     rp.add(this.startGame);
/* 126:151 */     rp.add(this.back);
/* 127:    */     
/* 128:153 */     return rp;
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected void layoutRootPane()
/* 132:    */   {
/* 133:159 */     int middleX = this.container.getWidth() / 2;
/* 134:160 */     int middleY = this.container.getHeight() / 2;
/* 135:    */     
/* 136:162 */     int maxX = this.container.getWidth();
/* 137:163 */     int maxY = this.container.getHeight();
/* 138:164 */     this.mapSelection.setSize(100, 20);
/* 139:165 */     this.mapSelection.setPosition(50, 50);
/* 140:166 */     this.mapList.setMinSize(200, 300);
/* 141:167 */     this.mapList.adjustSize();
/* 142:    */     
/* 143:169 */     this.mapList.setPosition(middleX / 4, 100);
/* 144:    */     
/* 145:    */ 
/* 146:172 */     this.MapName.setPosition(middleX, 100);
/* 147:173 */     this.Players.setPosition(middleX, 150);
/* 148:174 */     this.MapName.setSize(100, 20);
/* 149:175 */     this.Players.setSize(100, 20);
/* 150:    */     
/* 151:177 */     this.description.setPosition(middleX, middleY);
/* 152:178 */     this.description.setSize(middleX - 100, middleY - 250);
/* 153:    */     
/* 154:    */ 
/* 155:181 */     this.startGame.setSize(100, 20);
/* 156:182 */     this.startGame.setPosition(maxX - 200, maxY - 100);
/* 157:183 */     this.back.setSize(100, 20);
/* 158:184 */     this.back.setPosition(50, maxY - 100);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
/* 162:    */     throws SlickException
/* 163:    */   {}
/* 164:    */   
/* 165:    */   public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
/* 166:    */     throws SlickException
/* 167:    */   {}
/* 168:    */   
/* 169:    */   public int getID()
/* 170:    */   {
/* 171:207 */     return this.stateID;
/* 172:    */   }
/* 173:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.states.HostGameState
 * JD-Core Version:    0.7.0.1
 */