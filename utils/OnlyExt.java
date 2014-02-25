/*  1:   */ package sh.utils;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.FilenameFilter;
/*  5:   */ 
/*  6:   */ public class OnlyExt
/*  7:   */   implements FilenameFilter
/*  8:   */ {
/*  9:   */   String ext;
/* 10:   */   
/* 11:   */   public OnlyExt(String ext)
/* 12:   */   {
/* 13:11 */     this.ext = ("." + ext);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean accept(File dir, String name)
/* 17:   */   {
/* 18:16 */     return name.endsWith(this.ext);
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.utils.OnlyExt
 * JD-Core Version:    0.7.0.1
 */