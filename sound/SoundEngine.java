/*   1:    */ package sh.sound;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import org.newdawn.slick.Music;
/*   8:    */ import org.newdawn.slick.MusicListener;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ import org.newdawn.slick.Sound;
/*  11:    */ 
/*  12:    */ public class SoundEngine
/*  13:    */ {
/*  14:    */   public Music music;
/*  15:    */   public float musicVolume;
/*  16:    */   public float soundVolume;
/*  17:    */   
/*  18:    */   public float getMusicVolume()
/*  19:    */   {
/*  20: 16 */     return this.musicVolume;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public float getSoundVolume()
/*  24:    */   {
/*  25: 21 */     return this.soundVolume;
/*  26:    */   }
/*  27:    */   
/*  28: 27 */   public String directory = "data/sounds/";
/*  29:    */   public HashMap<String, Sound> sounds;
/*  30:    */   public List<String> musicList;
/*  31:    */   
/*  32:    */   public SoundEngine(boolean start, float mVolume, float sVolume)
/*  33:    */   {
/*  34: 32 */     this.musicVolume = (mVolume / 100.0F);
/*  35: 33 */     this.soundVolume = (sVolume / 100.0F);
/*  36:    */     
/*  37: 35 */     this.musicList = new ArrayList();
/*  38: 36 */     this.sounds = new HashMap();
/*  39:    */     try
/*  40:    */     {
/*  41: 40 */       this.music = new Music("data/sounds/music/DST_TacticalSpace.ogg");
/*  42:    */       
/*  43: 42 */       this.music.setVolume(this.musicVolume);
/*  44: 43 */       this.music.addListener(new MusicListener()
/*  45:    */       {
/*  46:    */         public void musicEnded(Music arg0) {}
/*  47:    */         
/*  48:    */         public void musicSwapped(Music arg0, Music arg1) {}
/*  49:    */       });
/*  50: 60 */       if (start) {
/*  51: 62 */         this.music.loop();
/*  52:    */       }
/*  53:    */     }
/*  54:    */     catch (SlickException e)
/*  55:    */     {
/*  56: 67 */       e.printStackTrace();
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void init()
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64: 76 */       Sound alien = new Sound(this.directory + "alien.wav");
/*  65: 77 */       this.sounds.put("alien", alien);
/*  66:    */       
/*  67: 79 */       Sound alienmelee = new Sound(this.directory + "alienmelee.wav");
/*  68: 80 */       this.sounds.put("alienmelee", alienmelee);
/*  69:    */       
/*  70: 82 */       Sound blip = new Sound(this.directory + "blip.wav");
/*  71: 83 */       this.sounds.put("blip", blip);
/*  72:    */       
/*  73: 85 */       Sound bolter = new Sound(this.directory + "bolter.wav");
/*  74: 86 */       this.sounds.put("bolter", bolter);
/*  75:    */       
/*  76: 88 */       Sound click = new Sound(this.directory + "overwatch.wav");
/*  77: 89 */       this.sounds.put("click", click);
/*  78:    */       
/*  79: 91 */       Sound door = new Sound(this.directory + "door.wav");
/*  80: 92 */       this.sounds.put("door", door);
/*  81:    */       
/*  82: 94 */       Sound flamer = new Sound(this.directory + "flamer.wav");
/*  83: 95 */       this.sounds.put("flamer", flamer);
/*  84:    */       
/*  85: 97 */       Sound marine = new Sound(this.directory + "marine.wav");
/*  86: 98 */       this.sounds.put("marine", marine);
/*  87:    */       
/*  88:100 */       Sound marinestep = new Sound(this.directory + "marinestep.wav");
/*  89:101 */       this.sounds.put("marinestep", marinestep);
/*  90:    */       
/*  91:103 */       Sound overwatch = new Sound(this.directory + "overwatch.wav");
/*  92:104 */       this.sounds.put("overwatch", overwatch);
/*  93:    */       
/*  94:106 */       Sound powerglove = new Sound(this.directory + "powerglove.wav");
/*  95:107 */       this.sounds.put("powerglove", powerglove);
/*  96:    */     }
/*  97:    */     catch (Exception localException) {}
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setMusicVolume(float volume)
/* 101:    */   {
/* 102:117 */     this.musicVolume = volume;
/* 103:119 */     if (volume == 0.0F) {
/* 104:121 */       this.music.stop();
/* 105:123 */     } else if (!this.music.playing()) {
/* 106:125 */       this.music.loop();
/* 107:    */     }
/* 108:127 */     this.music.setVolume(volume);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setSoundVolume(float volume)
/* 112:    */   {
/* 113:132 */     this.soundVolume = volume;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void playMusic(boolean loop)
/* 117:    */   {
/* 118:136 */     if (loop) {
/* 119:138 */       this.music.loop();
/* 120:    */     } else {
/* 121:142 */       this.music.play();
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void playSound(String name)
/* 126:    */   {
/* 127:148 */     System.out.println("Play sound: " + name + " " + this.soundVolume);
/* 128:149 */     System.out.println("Sound " + System.currentTimeMillis());
/* 129:150 */     ((Sound)this.sounds.get(name)).play(1.0F, this.soundVolume);
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.sound.SoundEngine
 * JD-Core Version:    0.7.0.1
 */