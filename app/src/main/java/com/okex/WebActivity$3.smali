.class Lcom/okex/WebActivity$3;
.super Landroid/webkit/WebViewClient;
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
    .line 113
    iput-object p1, p0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    invoke-direct {p0}, Landroid/webkit/WebViewClient;-><init>()V

    return-void
.end method


# virtual methods
.method public shouldOverrideUrlLoading(Landroid/webkit/WebView;Ljava/lang/String;)Z
    .registers 15
    .param p1, "view"    # Landroid/webkit/WebView;
    .param p2, "url"    # Ljava/lang/String;

    .prologue
    .line 116
    const-string v7, "intent://"

    invoke-virtual {p2, v7}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_39

    .line 118
    :try_start_8
    invoke-virtual {p1}, Landroid/webkit/WebView;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 119
    .local v0, "context":Landroid/content/Context;
    const/4 v7, 0x1

    invoke-static {p2, v7}, Landroid/content/Intent;->parseUri(Ljava/lang/String;I)Landroid/content/Intent;

    move-result-object v5

    .line 121
    .local v5, "intent":Landroid/content/Intent;
    if-eqz v5, :cond_37

    .line 122
    invoke-virtual {p1}, Landroid/webkit/WebView;->stopLoading()V

    .line 124
    invoke-virtual {v0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v6

    .line 125
    .local v6, "packageManager":Landroid/content/pm/PackageManager;
    const/high16 v7, 0x10000

    invoke-virtual {v6, v5, v7}, Landroid/content/pm/PackageManager;->resolveActivity(Landroid/content/Intent;I)Landroid/content/pm/ResolveInfo;

    move-result-object v4

    .line 126
    .local v4, "info":Landroid/content/pm/ResolveInfo;
    if-eqz v4, :cond_27

    .line 127
    invoke-virtual {v0, v5}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V

    .line 137
    :goto_25
    const/4 v7, 0x1

    .line 185
    .end local v0    # "context":Landroid/content/Context;
    .end local v4    # "info":Landroid/content/pm/ResolveInfo;
    .end local v5    # "intent":Landroid/content/Intent;
    .end local v6    # "packageManager":Landroid/content/pm/PackageManager;
    :goto_26
    return v7

    .line 129
    .restart local v0    # "context":Landroid/content/Context;
    .restart local v4    # "info":Landroid/content/pm/ResolveInfo;
    .restart local v5    # "intent":Landroid/content/Intent;
    .restart local v6    # "packageManager":Landroid/content/pm/PackageManager;
    :cond_27
    const-string v7, "browser_fallback_url"

    invoke-virtual {v5, v7}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 130
    .local v3, "fallbackUrl":Ljava/lang/String;
    invoke-virtual {p1, v3}, Landroid/webkit/WebView;->loadUrl(Ljava/lang/String;)V
    :try_end_30
    .catch Ljava/net/URISyntaxException; {:try_start_8 .. :try_end_30} :catch_31

    goto :goto_25

    .line 139
    .end local v0    # "context":Landroid/content/Context;
    .end local v3    # "fallbackUrl":Ljava/lang/String;
    .end local v4    # "info":Landroid/content/pm/ResolveInfo;
    .end local v5    # "intent":Landroid/content/Intent;
    .end local v6    # "packageManager":Landroid/content/pm/PackageManager;
    :catch_31
    move-exception v2

    .line 140
    .local v2, "e":Ljava/net/URISyntaxException;
    const-string v7, "Can\'t resolve intent://"

    invoke-static {v7}, Lcom/okex/LogUtil;->e(Ljava/lang/String;)V

    .line 185
    .end local v2    # "e":Ljava/net/URISyntaxException;
    :cond_37
    :goto_37
    const/4 v7, 0x0

    goto :goto_26

    .line 142
    :cond_39
    const-string v7, ".apk"

    invoke-virtual {p2, v7}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_cc

    .line 143
    iget-object v7, p0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    new-instance v8, Landroid/app/ProgressDialog;

    iget-object v9, p0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v10, p0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    const-string v11, "Theme.AppCompat.Light.Dialog"

    invoke-static {v10, v11}, Lcom/okex/util/IdUtil;->getStyleId(Landroid/content/Context;Ljava/lang/String;)I

    move-result v10

    invoke-direct {v8, v9, v10}, Landroid/app/ProgressDialog;-><init>(Landroid/content/Context;I)V

    iput-object v8, v7, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    .line 144
    iget-object v7, p0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v7, v7, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    const/4 v8, 0x1

    invoke-virtual {v7, v8}, Landroid/app/ProgressDialog;->setProgressStyle(I)V

    .line 145
    iget-object v7, p0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v7, v7, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    const/4 v8, 0x0

    invoke-virtual {v7, v8}, Landroid/app/ProgressDialog;->setCancelable(Z)V

    .line 146
    iget-object v7, p0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v7, v7, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    const/4 v8, 0x0

    invoke-virtual {v7, v8}, Landroid/app/ProgressDialog;->setCanceledOnTouchOutside(Z)V

    .line 147
    iget-object v7, p0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v7, v7, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    const-string v8, "\u5b89\u88c5\u66f4\u65b0"

    invoke-virtual {v7, v8}, Landroid/app/ProgressDialog;->setTitle(Ljava/lang/CharSequence;)V

    .line 148
    iget-object v7, p0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v7, v7, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    const/4 v8, 0x0

    invoke-virtual {v7, v8}, Landroid/app/ProgressDialog;->setIndeterminate(Z)V

    .line 149
    iget-object v7, p0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v7, v7, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    const-string v8, "\u6b63\u5728\u4e0b\u8f7d..."

    invoke-virtual {v7, v8}, Landroid/app/ProgressDialog;->setMessage(Ljava/lang/CharSequence;)V

    .line 150
    iget-object v7, p0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v7, v7, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    const/4 v8, 0x0

    invoke-virtual {v7, v8}, Landroid/app/ProgressDialog;->setProgress(I)V

    .line 151
    iget-object v7, p0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    iget-object v7, v7, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v7}, Landroid/app/ProgressDialog;->show()V

    .line 153
    move-object v1, p2

    .line 154
    .local v1, "downloadUrl":Ljava/lang/String;
    sget-object v7, Lcom/okex/OkManager;->chennelApkUrl:Ljava/lang/String;

    invoke-static {v7}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v7

    if-nez v7, :cond_a0

    .line 155
    sget-object v1, Lcom/okex/OkManager;->chennelApkUrl:Ljava/lang/String;

    .line 157
    :cond_a0
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "download apk originurl:"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, "  chennelApkUrl:"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    sget-object v8, Lcom/okex/OkManager;->chennelApkUrl:Ljava/lang/String;

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Lcom/okex/LogUtil;->i(Ljava/lang/String;)V

    .line 158
    new-instance v7, Lcom/okex/WebActivity$3$1;

    invoke-direct {v7, p0}, Lcom/okex/WebActivity$3$1;-><init>(Lcom/okex/WebActivity$3;)V

    invoke-static {v1, v7}, Lcom/okex/util/DownloadFileTool;->doDownloadThread(Ljava/lang/String;Lcom/okex/util/DownloadFileTool$OnDownloadListener;)V

    goto/16 :goto_37

    .line 181
    .end local v1    # "downloadUrl":Ljava/lang/String;
    :cond_cc
    const-string v7, "https"

    invoke-virtual {p2, v7}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-nez v7, :cond_dc

    const-string v7, "http"

    invoke-virtual {p2, v7}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_37

    .line 182
    :cond_dc
    iget-object v7, p0, Lcom/okex/WebActivity$3;->this$0:Lcom/okex/WebActivity;

    invoke-static {v7}, Lcom/okex/WebActivity;->access$000(Lcom/okex/WebActivity;)Landroid/webkit/WebView;

    move-result-object v7

    invoke-virtual {v7, p2}, Landroid/webkit/WebView;->loadUrl(Ljava/lang/String;)V

    .line 183
    const/4 v7, 0x1

    goto/16 :goto_26
.end method
