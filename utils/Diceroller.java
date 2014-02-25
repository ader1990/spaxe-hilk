/*  1:   */ package sh.utils;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.util.Random;
/*  5:   */ 
/*  6:   */ public class Diceroller
/*  7:   */ {
/*  8:   */   private static long previousSeed;
/*  9: 7 */   private static Random random = new Random();
/* 10:   */   
/* 11:   */   public static int[] roll(int max, int rolls)
/* 12:   */   {
/* 13:11 */     int[] results = new int[rolls];
/* 14:12 */     long seed = System.currentTimeMillis();
/* 15:13 */     if (seed == previousSeed) {
/* 16:15 */       seed += 1L;
/* 17:   */     }
/* 18:18 */     random = new Random(seed);
/* 19:19 */     for (int i = 0; i < rolls; i++) {
/* 20:21 */       results[i] = (random.nextInt(max) + 1);
/* 21:   */     }
/* 22:24 */     previousSeed = seed;
/* 23:   */     
/* 24:26 */     return results;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static int roll(int max)
/* 28:   */   {
/* 29:31 */     System.out.println("Max " + max);
/* 30:32 */     long seed = System.currentTimeMillis();
/* 31:33 */     if (seed == previousSeed) {
/* 32:35 */       seed += 1L;
/* 33:   */     }
/* 34:38 */     random = new Random(seed);
/* 35:   */     
/* 36:40 */     int result = random.nextInt(max) + 1;
/* 37:   */     
/* 38:   */ 
/* 39:43 */     previousSeed = seed;
/* 40:   */     
/* 41:45 */     return result;
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.utils.Diceroller
 * JD-Core Version:    0.7.0.1
 */