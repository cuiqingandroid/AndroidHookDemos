.class Lcom/okex/WebActivity$3$1;
.super Ljava/lang/Object;
.source "WebActivity.java"

# interfaces
.implements Lcom/okex/util/DownloadFileTool$OnDownloadListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/okex/WebActivity$3;->shouldOverrideUrlLoading(Landroid/webkit/WebView;Ljava/lang/String;)Z
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/okex/WebActivity$3;


# direct methods
.method constructor <init>(Lcom/okex/WebActivity$3;)V
    .registers 2
    .param p1, "this$1"    # Lcom/okex/WebActivity$3;

    .prologue
    .line 158
    iput-object p1, p0, Lcom/okex/WebActivity$3$1;->this$1:Lcom/okex/WebActivity$3;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onDownloadFailed()V
    .registers 2

    .prologue
    .line 176
    iget-object v0, p0, Lcom/okex/WebActivity$3$1;->this$1:Lcom/okex/WebActivity$3;

    iget-object v0, v0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v0, v0, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    if-eqz v0, :cond_11

    .line 177
    iget-object v0, p0, Lcom/okex/WebActivity$3$1;->this$1:Lcom/okex/WebActivity$3;

    iget-object v0, v0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v0, v0, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->dismiss()V

    .line 179
    :cond_11
    return-void
.end method

.method public onDownloadSuccess(Ljava/lang/String;)V
    .registers 3
    .param p1, "filepath"    # Ljava/lang/String;

    .prologue
    .line 161
    iget-object v0, p0, Lcom/okex/WebActivity$3$1;->this$1:Lcom/okex/WebActivity$3;

    iget-object v0, v0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v0, v0, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    if-eqz v0, :cond_11

    .line 162
    iget-object v0, p0, Lcom/okex/WebActivity$3$1;->this$1:Lcom/okex/WebActivity$3;

    iget-object v0, v0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v0, v0, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->dismiss()V

    .line 164
    :cond_11
    iget-object v0, p0, Lcom/okex/WebActivity$3$1;->this$1:Lcom/okex/WebActivity$3;

    iget-object v0, v0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    invoke-static {v0, p1}, Lcom/okex/util/DownloadFileTool;->installApk(Landroid/content/Context;Ljava/lang/String;)V

    .line 165
    return-void
.end method

.method public onDownloading(I)V
    .registers 3
    .param p1, "progress"    # I

    .prologue
    .line 169
    iget-object v0, p0, Lcom/okex/WebActivity$3$1;->this$1:Lcom/okex/WebActivity$3;

    iget-object v0, v0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v0, v0, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    if-eqz v0, :cond_11

    .line 170
    iget-object v0, p0, Lcom/okex/WebActivity$3$1;->this$1:Lcom/okex/WebActivity$3;

    iget-object v0, v0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v0, v0, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v0, p1}, Landroid/app/ProgressDialog;->setProgress(I)V

    .line 172
    :cond_11
    return-void
.end method
