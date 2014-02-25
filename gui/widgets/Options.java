/*   1:    */ package sh.gui.widgets;
/*   2:    */ 
/*   3:    */ import de.matthiasmann.twl.Button;
/*   4:    */ import de.matthiasmann.twl.EditField;
/*   5:    */ import de.matthiasmann.twl.Label;
/*   6:    */ import de.matthiasmann.twl.MenuCheckbox;
/*   7:    */ import de.matthiasmann.twl.ResizableFrame;
/*   8:    */ import de.matthiasmann.twl.ResizableFrame.ResizableAxis;
/*   9:    */ import de.matthiasmann.twl.Scrollbar;
/*  10:    */ import de.matthiasmann.twl.Scrollbar.Orientation;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import sh.SpaceHulkGame;
/*  13:    */ import sh.SpaceHulkGameContainer;
/*  14:    */ 
/*  15:    */ public class Options
/*  16:    */   extends ResizableFrame
/*  17:    */ {
/*  18:    */   Button apply;
/*  19:    */   Button back;
/*  20:    */   SpaceHulkGameContainer shgameContainer;
/*  21:    */   SpaceHulkGame game;
/*  22:    */   Scrollbar musicVolume;
/*  23:    */   Label mLabel;
/*  24:    */   Label sLabel;
/*  25:    */   Label nLabel;
/*  26:    */   Label iLabel;
/*  27:    */   Scrollbar soundVolume;
/*  28:    */   EditField name;
/*  29:    */   EditField ip;
/*  30:    */   MenuCheckbox fullscreen;
/*  31:    */   
/*  32:    */   public Options(SpaceHulkGameContainer cont, SpaceHulkGame shgame)
/*  33:    */   {
/*  34: 43 */     setResizableAxis(ResizableFrame.ResizableAxis.NONE);
/*  35: 44 */     setDraggable(true);
/*  36: 45 */     setTheme("infobox");
/*  37: 46 */     this.shgameContainer = cont;
/*  38: 47 */     this.game = shgame;
/*  39: 48 */     this.apply = new Button("Apply");
/*  40:    */     
/*  41: 50 */     this.ip = new EditField();
/*  42: 51 */     this.ip.setText(this.shgameContainer.getAdres());
/*  43:    */     
/*  44: 53 */     this.ip = new EditField();
/*  45: 54 */     this.ip.setText(this.shgameContainer.getAdres());
/*  46:    */     
/*  47: 56 */     this.mLabel = new Label();
/*  48: 57 */     this.mLabel.setText("Music Volume");
/*  49: 58 */     this.sLabel = new Label();
/*  50: 59 */     this.sLabel.setText("Sound Volume");
/*  51: 60 */     this.nLabel = new Label();
/*  52: 61 */     this.nLabel.setText("User name");
/*  53: 62 */     this.iLabel = new Label();
/*  54: 63 */     this.iLabel.setText("IP Address");
/*  55:    */     
/*  56: 65 */     this.name = new EditField();
/*  57: 66 */     this.name.setText(this.shgameContainer.getName());
/*  58:    */     
/*  59: 68 */     this.musicVolume = new Scrollbar(Scrollbar.Orientation.HORIZONTAL);
/*  60: 69 */     this.musicVolume.setMinMaxValue(0, 100);
/*  61:    */     
/*  62: 71 */     int value = (int)(this.shgameContainer.getMusicAudioVolume() * 100.0F);
/*  63: 72 */     this.musicVolume.setValue(value);
/*  64:    */     
/*  65: 74 */     this.soundVolume = new Scrollbar(Scrollbar.Orientation.HORIZONTAL);
/*  66: 75 */     this.soundVolume.setMinMaxValue(0, 100);
/*  67: 76 */     System.out.println("Set volume bar: " + this.shgameContainer.getSoundAudioVolume());
/*  68: 77 */     value = (int)(this.shgameContainer.getSoundAudioVolume() * 100.0F);
/*  69: 78 */     this.soundVolume.setValue(value);
/*  70:    */     
/*  71: 80 */     this.apply.addCallback(new Runnable()
/*  72:    */     {
/*  73:    */       public void run()
/*  74:    */       {
/*  75: 86 */         String ipadres = Options.this.ip.getText();
/*  76: 87 */         String username = Options.this.name.getText();
/*  77: 88 */         float mVolume = Options.this.musicVolume.getValue() / 100.0F;
/*  78: 89 */         float sVolume = Options.this.soundVolume.getValue();
/*  79:    */         
/*  80: 91 */         sVolume /= 100.0F;
/*  81: 92 */         System.out.println("Apply " + sVolume);
/*  82: 93 */         if (!username.isEmpty()) {
/*  83: 95 */           Options.this.shgameContainer.writeProperty("name", username);
/*  84:    */         }
/*  85: 97 */         if (!ipadres.isEmpty()) {
/*  86: 99 */           Options.this.shgameContainer.writeProperty("adres", ipadres);
/*  87:    */         }
/*  88:101 */         Options.this.shgameContainer.setSoundAudioVolume(sVolume);
/*  89:102 */         Options.this.shgameContainer.setMusicAudioVolume(mVolume);
/*  90:103 */         Options.this.shgameContainer.writeProperty("music", String.valueOf(Options.this.musicVolume.getValue()));
/*  91:104 */         Options.this.shgameContainer.writeProperty("sound", String.valueOf(Options.this.soundVolume.getValue()));
/*  92:    */         
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:129 */         Options.this.shgameContainer.playSound("click");
/* 117:    */       }
/* 118:133 */     });
/* 119:134 */     this.back = new Button("Back");
/* 120:135 */     this.back.addCallback(new Runnable()
/* 121:    */     {
/* 122:    */       public void run()
/* 123:    */       {
/* 124:140 */         Options.this.shgameContainer.playSound("click");
/* 125:141 */         Options.this.game.enterState(0);
/* 126:    */       }
/* 127:145 */     });
/* 128:146 */     add(this.musicVolume);
/* 129:147 */     add(this.soundVolume);
/* 130:148 */     add(this.name);
/* 131:149 */     add(this.apply);
/* 132:150 */     add(this.back);
/* 133:151 */     add(this.ip);
/* 134:    */     
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:157 */     add(this.mLabel);
/* 140:158 */     add(this.sLabel);
/* 141:159 */     add(this.nLabel);
/* 142:160 */     add(this.iLabel);
/* 143:    */   }
/* 144:    */   
/* 145:    */   protected void layout()
/* 146:    */   {
/* 147:170 */     int middleX = this.shgameContainer.getWidth() / 2;
/* 148:171 */     int middleY = this.shgameContainer.getHeight() / 2;
/* 149:    */     
/* 150:173 */     int maxX = this.shgameContainer.getWidth();
/* 151:174 */     int maxY = this.shgameContainer.getHeight();
/* 152:    */     
/* 153:176 */     int px = getWidth() + getX();
/* 154:177 */     int py = getHeight() + getY();
/* 155:    */     
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:191 */     this.mLabel.setPosition(middleX - 105, middleY - 60);
/* 169:192 */     this.mLabel.setSize(100, 20);
/* 170:193 */     this.musicVolume.setPosition(middleX + 5, middleY - 60);
/* 171:194 */     this.musicVolume.setSize(100, 20);
/* 172:    */     
/* 173:196 */     this.sLabel.setPosition(middleX - 105, middleY - 35);
/* 174:197 */     this.sLabel.setSize(100, 20);
/* 175:198 */     this.soundVolume.setPosition(middleX + 5, middleY - 35);
/* 176:199 */     this.soundVolume.setSize(100, 20);
/* 177:    */     
/* 178:201 */     this.nLabel.setPosition(middleX - 105, middleY - 10);
/* 179:202 */     this.nLabel.setSize(100, 20);
/* 180:203 */     this.name.setPosition(middleX + 5, middleY - 10);
/* 181:204 */     this.name.setSize(100, 20);
/* 182:    */     
/* 183:206 */     this.iLabel.setPosition(middleX - 105, middleY + 15);
/* 184:207 */     this.iLabel.setSize(100, 20);
/* 185:208 */     this.ip.setPosition(middleX + 5, middleY + 15);
/* 186:209 */     this.ip.setSize(100, 20);
/* 187:    */     
/* 188:211 */     this.apply.setPosition(px - 150, py - 35);
/* 189:212 */     this.apply.setSize(100, 20);
/* 190:    */     
/* 191:214 */     this.back.setPosition(getX() + 50, py - 35);
/* 192:215 */     this.back.setSize(100, 20);
/* 193:    */   }
/* 194:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.widgets.Options
 * JD-Core Version:    0.7.0.1
 */