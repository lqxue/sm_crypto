/*    */ package go;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Universe
/*    */ {
/*    */   static {
/* 11 */     Seq.touch();
/* 12 */     _init();
/*    */   }
/*    */   
/*    */   private static native void _init();
/*    */   
/*    */   public static void touch() {}
/*    */   
/*    */   private static final class proxyerror
/*    */     extends Exception
/*    */     implements Seq.Proxy, error
/*    */   {
/*    */     private final int refnum;
/*    */     
/*    */     public final int incRefnum() {
/* 26 */       Seq.incGoRef(this.refnum, this);
/* 27 */       return this.refnum;
/*    */     }
/*    */     proxyerror(int param1Int) {
/* 30 */       this.refnum = param1Int; Seq.trackGoRef(param1Int, this);
/*    */     } public String getMessage() {
/* 32 */       return error();
/*    */     }
/*    */     
/*    */     public native String error();
/*    */   }
/*    */ }


/* Location:              /Users/lqx/Desktop/sm_crypto/classes.jar!/go/Universe.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */