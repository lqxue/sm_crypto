/*    */ package sm_crypto;
/*    */ 
/*    */ import go.Seq;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Sm_crypto
/*    */ {
/*    */   static {
/* 11 */     Seq.touch();
/* 12 */     _init();
/*    */   }
/*    */   
/*    */   public static native boolean c_VerifySignature(String paramString1, String paramString2, String paramString3);
/*    */   
/*    */   public static native String c_Sign(String paramString1, String paramString2);
/*    */   
/*    */   public static native String c_Hash256Bysm3(String paramString);
/*    */   
/*    */   public static native String c_Hash256Bysha3_Contract(String paramString);
/*    */   
/*    */   public static native String c_Hash256Bysha3(String paramString);
/*    */   
/*    */   public static native String c_GenerateKey();
/*    */   
/*    */   public static native String c_FromPrvKey(String paramString);
/*    */   
/*    */   public static native String c_AddrFromPub(String paramString);
/*    */   
/*    */   public static native String c_AddrFromPrv(String paramString);
/*    */   
/*    */   private static native void _init();
/*    */   
/*    */   public static void touch() {}
/*    */ }


/* Location:              /Users/lqx/Desktop/sm_crypto/classes.jar!/sm_crypto/Sm_crypto.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */