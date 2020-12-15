.class Lcom/okex/WebActivity$2;
.super Landroid/webkit/WebChromeClient;
.source "WebActivity.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/okex/WebActivity;->onCreate(Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/okex/WebActivity;


# direct methods
.method constructor <init>(Lcom/okex/WebActivity;)V
    .registers 2
    .param p1, "this$0"    # Lcom/okex/WebActivity;

    .prologue
    .line 72
    iput-object p1, p0, Lcom/okex/WebActivity$2;->this$0:Lcom/okex/WebActivity;

    invoke-direct {p0}, Landroid/webkit/WebChromeClient;-><init>()V

    return-void
.end method


# virtual methods
.method public onProgressChanged(Landroid/webkit/WebView;I)V
    .registers 3
    .param p1, "view"    # Landroid/webkit/WebView;
    .param p2, "newProgress"    # I

    .prologue
    .line 108
    invoke-super {p0, p1, p2}, Landroid/webkit/WebChromeClient;->onProgressChanged(Landroid/webkit/WebView;I)V

    .line 109
    return-void
.end method

.method public onReceivedTitle(Landroid/webkit/WebView;Ljava/lang/String;)V
    .registers 3
    .param p1, "view"    # Landroid/webkit/WebView;
    .param p2, "str"    # Ljava/lang/String;

    .prologue
    .line 76
    invoke-super {p0, p1, p2}, Landroid/webkit/WebChromeClient;->onReceivedTitle(Landroid/webkit/WebView;Ljava/lang/String;)V

    .line 77
    return-void
.end method

.method public onShowFileChooser(Landroid/webkit/WebView;Landroid/webkit/ValueCallback;Landroid/webkit/WebChromeClient$FileChooserParams;)Z
    .registers 8
    .param p1, "webView"    # Landroid/webkit/WebView;
    .param p3, "fileChooserParams"    # Landroid/webkit/WebChromeClient$FileChooserParams;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/webkit/WebView;",
            "Landroid/webkit/ValueCallback",
            "<[",
            "Landroid/net/Uri;",
            ">;",
            "Landroid/webkit/WebChromeClient$FileChooserParams;",
            ")Z"
        }
    .end annotation

    .prologue
    .local p2, "filePathCallback":Landroid/webkit/ValueCallback;, "Landroid/webkit/ValueCallback<[Landroid/net/Uri;>;"
    const/4 v3, 0x2

    .line 94
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x15

    if-lt v1, v2, :cond_25

    .line 95
    new-instance v0, Landroid/content/Intent;

    const-string v1, "android.intent.action.GET_CONTENT"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 96
    .local v0, "i":Landroid/content/Intent;
    const-string v1, "android.intent.category.OPENABLE"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->addCategory(Ljava/lang/String;)Landroid/content/Intent;

    .line 97
    const-string v1, "image/*"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setType(Ljava/lang/String;)Landroid/content/Intent;

    .line 98
    iget-object v1, p0, Lcom/okex/WebActivity$2;->this$0:Lcom/okex/WebActivity;

    const-string v2, "File Chooser"

    invoke-static {v0, v2}, Landroid/content/Intent;->createChooser(Landroid/content/Intent;Ljava/lang/CharSequence;)Landroid/content/Intent;

    move-result-object v2

    invoke-virtual {v1, v2, v3}, Lcom/okex/WebActivity;->startActivityForResult(Landroid/content/Intent;I)V

    .line 103
    :goto_23
    const/4 v1, 0x1

    return v1

    .line 100
    .end local v0    # "i":Landroid/content/Intent;
    :cond_25
    new-instance v0, Landroid/content/Intent;

    const-string v1, "android.intent.action.PICK"

    sget-object v2, Landroid/provider/MediaStore$Images$Media;->EXTERNAL_CONTENT_URI:Landroid/net/Uri;

    invoke-direct {v0, v1, v2}, Landroid/content/Intent;-><init>(Ljava/lang/String;Landroid/net/Uri;)V

    .line 101
    .restart local v0    # "i":Landroid/content/Intent;
    iget-object v1, p0, Lcom/okex/WebActivity$2;->this$0:Lcom/okex/WebActivity;

    invoke-virtual {v1, v0, v3}, Lcom/okex/WebActivity;->startActivityForResult(Landroid/content/Intent;I)V

    goto :goto_23
.end method

.method public openFileChooser(Landroid/webkit/ValueCallback;Ljava/lang/String;Ljava/lang/String;)V
    .registers 8
    .param p2, "acceptType"    # Ljava/lang/String;
    .param p3, "capture"    # Ljava/lang/String;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/webkit/ValueCallback",
            "<",
            "Landroid/net/Uri;",
            ">;",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ")V"
        }
    .end annotation

    .prologue
    .local p1, "uploadMsg":Landroid/webkit/ValueCallback;, "Landroid/webkit/ValueCallback<Landroid/net/Uri;>;"
    const/4 v3, 0x2

    .line 81
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x15

    if-lt v1, v2, :cond_24

    .line 82
    new-instance v0, Landroid/content/Intent;

    const-string v1, "android.intent.action.GET_CONTENT"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 83
    .local v0, "i":Landroid/content/Intent;
    const-string v1, "android.intent.category.OPENABLE"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->addCategory(Ljava/lang/String;)Landroid/content/Intent;

    .line 84
    const-string v1, "image/*"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setType(Ljava/lang/String;)Landroid/content/Intent;

    .line 85
    iget-object v1, p0, Lcom/okex/WebActivity$2;->this$0:Lcom/okex/WebActivity;

    const-string v2, "File Chooser"

    invoke-static {v0, v2}, Landroid/content/Intent;->createChooser(Landroid/content/Intent;Ljava/lang/CharSequence;)Landroid/content/Intent;

    move-result-object v2

    invoke-virtual {v1, v2, v3}, Lcom/okex/WebActivity;->startActivityForResult(Landroid/content/Intent;I)V

    .line 90
    :goto_23
    return-void

    .line 87
    .end local v0    # "i":Landroid/content/Intent;
    :cond_24
    new-instance v0, Landroid/content/Intent;

    const-string v1, "android.intent.action.PICK"

    sget-object v2, Landroid/provider/MediaStore$Images$Media;->EXTERNAL_CONTENT_URI:Landroid/net/Uri;

    invoke-direct {v0, v1, v2}, Landroid/content/Intent;-><init>(Ljava/lang/String;Landroid/net/Uri;)V

    .line 88
    .restart local v0    # "i":Landroid/content/Intent;
    iget-object v1, p0, Lcom/okex/WebActivity$2;->this$0:Lcom/okex/WebActivity;

    invoke-virtual {v1, v0, v3}, Lcom/okex/WebActivity;->startActivityForResult(Landroid/content/Intent;I)V

    goto :goto_23
.end method
