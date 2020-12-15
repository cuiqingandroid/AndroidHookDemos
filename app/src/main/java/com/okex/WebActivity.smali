.class public Lcom/okex/WebActivity;
.super Landroid/app/Activity;
.source "WebActivity.java"


# static fields
.field public static final FILE_CHOOSER_RESULT_CODE:I = 0x2


# instance fields
.field private mWebView:Landroid/webkit/WebView;

.field progressDialog:Landroid/app/ProgressDialog;


# direct methods
.method public constructor <init>()V
    .registers 2

    .prologue
    .line 25
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 30
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/okex/WebActivity;->progressDialog:Landroid/app/ProgressDialog;

    return-void
.end method

.method static synthetic access$000(Lcom/okex/WebActivity;)Landroid/webkit/WebView;
    .registers 2
    .param p0, "x0"    # Lcom/okex/WebActivity;

    .prologue
    .line 25
    iget-object v0, p0, Lcom/okex/WebActivity;->mWebView:Landroid/webkit/WebView;

    return-object v0
.end method


# virtual methods
.method public onBackPressed()V
    .registers 2

    .prologue
    .line 198
    iget-object v0, p0, Lcom/okex/WebActivity;->mWebView:Landroid/webkit/WebView;

    if-eqz v0, :cond_12

    iget-object v0, p0, Lcom/okex/WebActivity;->mWebView:Landroid/webkit/WebView;

    invoke-virtual {v0}, Landroid/webkit/WebView;->canGoBack()Z

    move-result v0

    if-eqz v0, :cond_12

    .line 199
    iget-object v0, p0, Lcom/okex/WebActivity;->mWebView:Landroid/webkit/WebView;

    invoke-virtual {v0}, Landroid/webkit/WebView;->goBack()V

    .line 203
    :goto_11
    return-void

    .line 201
    :cond_12
    invoke-super {p0}, Landroid/app/Activity;->onBackPressed()V

    goto :goto_11
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .registers 8
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;
    .annotation build Landroid/annotation/SuppressLint;
        value = {
            "ResourceType"
        }
    .end annotation

    .prologue
    const/4 v5, 0x0

    const/4 v4, 0x1

    .line 34
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 35
    invoke-static {p0}, Lcom/okex/util/DownloadFileTool;->init(Landroid/content/Context;)V

    .line 36
    const-string v2, "activity_web_ex"

    invoke-static {p0, v2}, Lcom/okex/util/IdUtil;->getLayoutId(Landroid/content/Context;Ljava/lang/String;)I

    move-result v2

    invoke-virtual {p0, v2}, Lcom/okex/WebActivity;->setContentView(I)V

    .line 37
    const-string v2, "webViewEx"

    invoke-static {p0, v2}, Lcom/okex/util/IdUtil;->getViewId(Landroid/content/Context;Ljava/lang/String;)I

    move-result v2

    invoke-virtual {p0, v2}, Lcom/okex/WebActivity;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/webkit/WebView;

    iput-object v2, p0, Lcom/okex/WebActivity;->mWebView:Landroid/webkit/WebView;

    .line 38
    iget-object v2, p0, Lcom/okex/WebActivity;->mWebView:Landroid/webkit/WebView;

    new-instance v3, Lcom/okex/WebActivity$1;

    invoke-direct {v3, p0}, Lcom/okex/WebActivity$1;-><init>(Lcom/okex/WebActivity;)V

    invoke-virtual {v2, v3}, Landroid/webkit/WebView;->setDownloadListener(Landroid/webkit/DownloadListener;)V

    .line 54
    iget-object v2, p0, Lcom/okex/WebActivity;->mWebView:Landroid/webkit/WebView;

    const/16 v3, 0x19

    invoke-virtual {v2, v3}, Landroid/webkit/WebView;->setInitialScale(I)V

    .line 56
    iget-object v2, p0, Lcom/okex/WebActivity;->mWebView:Landroid/webkit/WebView;

    invoke-virtual {v2}, Landroid/webkit/WebView;->getSettings()Landroid/webkit/WebSettings;

    move-result-object v1

    .line 57
    .local v1, "webSettings":Landroid/webkit/WebSettings;
    invoke-virtual {v1, v4}, Landroid/webkit/WebSettings;->setJavaScriptEnabled(Z)V

    .line 58
    invoke-virtual {v1, v5}, Landroid/webkit/WebSettings;->setBlockNetworkImage(Z)V

    .line 59
    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x15

    if-lt v2, v3, :cond_45

    .line 60
    invoke-virtual {v1, v5}, Landroid/webkit/WebSettings;->setMixedContentMode(I)V

    .line 62
    :cond_45
    invoke-virtual {v1, v4}, Landroid/webkit/WebSettings;->setUseWideViewPort(Z)V

    .line 63
    invoke-virtual {v1, v4}, Landroid/webkit/WebSettings;->setLoadWithOverviewMode(Z)V

    .line 64
    invoke-virtual {v1, v4}, Landroid/webkit/WebSettings;->setBuiltInZoomControls(Z)V

    .line 65
    invoke-virtual {v1, v4}, Landroid/webkit/WebSettings;->setSupportZoom(Z)V

    .line 66
    invoke-virtual {v1, v5}, Landroid/webkit/WebSettings;->setDisplayZoomControls(Z)V

    .line 67
    invoke-virtual {v1, v4}, Landroid/webkit/WebSettings;->setJavaScriptCanOpenWindowsAutomatically(Z)V

    .line 68
    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x11

    if-lt v2, v3, :cond_60

    .line 69
    invoke-virtual {v1, v5}, Landroid/webkit/WebSettings;->setMediaPlaybackRequiresUserGesture(Z)V

    .line 72
    :cond_60
    new-instance v0, Lcom/okex/WebActivity$2;

    invoke-direct {v0, p0}, Lcom/okex/WebActivity$2;-><init>(Lcom/okex/WebActivity;)V

    .line 112
    .local v0, "webChromeClient":Landroid/webkit/WebChromeClient;
    iget-object v2, p0, Lcom/okex/WebActivity;->mWebView:Landroid/webkit/WebView;

    invoke-virtual {v2, v0}, Landroid/webkit/WebView;->setWebChromeClient(Landroid/webkit/WebChromeClient;)V

    .line 113
    iget-object v2, p0, Lcom/okex/WebActivity;->mWebView:Landroid/webkit/WebView;

    new-instance v3, Lcom/okex/WebActivity$3;

    invoke-direct {v3, p0}, Lcom/okex/WebActivity$3;-><init>(Lcom/okex/WebActivity;)V

    invoke-virtual {v2, v3}, Landroid/webkit/WebView;->setWebViewClient(Landroid/webkit/WebViewClient;)V

    .line 190
    iget-object v2, p0, Lcom/okex/WebActivity;->mWebView:Landroid/webkit/WebView;

    invoke-virtual {v2}, Landroid/webkit/WebView;->getSettings()Landroid/webkit/WebSettings;

    move-result-object v2

    invoke-virtual {v2, v4}, Landroid/webkit/WebSettings;->setDomStorageEnabled(Z)V

    .line 192
    iget-object v2, p0, Lcom/okex/WebActivity;->mWebView:Landroid/webkit/WebView;

    const-string v3, "https://www.okexcn.com/join/1930144"

    invoke-virtual {v2, v3}, Landroid/webkit/WebView;->loadUrl(Ljava/lang/String;)V

    .line 193
    return-void
.end method
