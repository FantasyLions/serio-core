package com.serio.core.utils;

/**
 * 常量类
 * @author caiys
 *
 */
public class Constants {
    public static String[] sensitive = {"|","&",";","$","%","@","'","<",">","(",")","+","cr","lf","select",
                                        "from","update","delete","union","where","script","document","eval"};
	/*cuc 轮播图片，普通图片缩略图，栏目logo*/
//	1.轮播图片:过大resize到指定大小，删除源文件
//	2.logo和公告:过大resize到指定大小，删除源文件
//	3.图片相册:保留源文件,并截取一张小图
	public static final String NORMAL_BIG_PIC = "1";
	public static final String SPECIAL_BIG_PIC = "2";
	public static final String NORMAL_PIC = "3";
	public static final String LOGO_PIC = "4";
	public static final String MEDIA_COVER = "5";
	
	/* 分类信息  cuc*/
//	public static final String CATEGORY_TYPE_INDEX = "0";//首页
//	public static final String CATEGORY_TYPE_NEW = "1";//新闻
//	public static final String CATEGORY_TYPE_TEACH = "2";//专辑
	public static final String CATEGORY_TYPE_ORIGIN = "3";//原创
//	public static final String CATEGORY_TYPE_PROGRAM = "4";//栏目
//	public static final String CATEGORY_TYPE_VIRTUAL = "6";//虚拟电视台
//	public static final String CATEGORY_TYPE_COMMENT = "7";//评论维护
//	public static final String CATEGORY_TYPE_SPECIAL = "8";//专题活动
	
	/* 子分类信息  cuc*/
//	public static final String CATEGORY_SUBTYPE_MEDIA = "1";//视频
//	public static final String CATEGORY_SUBTYPE_PICTURE = "2";//图片新闻
//	public static final String CATEGORY_SUBTYPE_BIG_PICTURE = "3";//大图预览
//	public static final String CATEGORY_SUBTYPE_COMMON = "4";//公告
//	public static final String CATEGORY_SUBTYPE_COMMENT = "5";//点播评论维护
//	public static final String CATEGORY_SUBTYPE_LIVECOMMENT = "6";//直播评论维护
	
	/*news模块级类型同category cuc*/
	
	/*视频类型 模块类型同category cuc*/
//	public static final String MEDIA_SUBTYPE_TRAILER = "1";//宣传片
//	public static final String MEDIA_SUBTYPE_FEATURE = "2";//正片
//	public static final String MEDIA_SUBTYPE_SHORT = "3";//短片
//	public static final String MEDIA_SUBTYPE_VIRTUAL = "4";
	
	/* 评论类型 */
//	public static final String COMMENT_COMMON = "0";//普通评论
//	public static final String COMMENT_FEEDBACK = "1";//在线反馈
	
//	/* 分类种类*/
//	public static final String CATEGORY_TYPE_NEWS = "0";
//	public static final String CATEGORY_TYPE_MEDIA = "1";
//	public static final String CATEGORY_TYPE_TVINDUCTION = "2";
//	public static final String CATEGORY_TYPE_VIRTUALPROGRAM = "6";//虚拟电视台分类
//	/* 图片新闻分类 */
//	public static final String IMAGENEWS_TYPE_SHOOT = "Shoot";						// 行行摄摄
//	public static final String IMAGENEWS_TYPE_PREVIEW = "Preview";					// 首页预览
//	// 新闻类型
//	public static final String NEWS_TYPE_COMMON = "0";			// 普通新闻
//	public static final String NEWS_TYPE_PICTURE = "1"; 		// 图片新闻
//	public static final String NEWS_TYPE_BIG_PICTURE = "3"; 	// 专题活动预览图片
//	public static final Integer NEWS_TYPE_COMMON_ID = 19;		// 普通新闻标识
//	public static final Integer NEWS_TYPE_PICTURE_ID = 20; 		// 图片新闻标识
//	// 分类父节点ID
//	public static final String PAGE_PREVIEW_TYPE = "3";				// 首页预览 
//	public static final String IMAGE_CLASSIFICATION_TYPE = "2";		// 图片分类
//	public static final String VIDEO_ON_DEMAND_TYPE = "1";			// 视频点播
	/* 其他文件仍保留的分类，与当前项目无关 */
//	public static final String MEDIA_TYPE_CAMPUS_SHOOT = "CAMPUS_SHOOT";						// 校园拍客
//	public static final String MEDIA_TYPE_COMPUS_STATION_PROGRAM = "COMPUS_STATION_PROGRAM";	// 校台节目
//	public static final String MEDIA_TYPE_TEACH_VIDEO = "TEACH_VIDEO";							// 专辑视频
//	public static final String MEDIA_TYPE_OBSERVE_THEATER = "OBSERVE_THEATER";					// 观摩剧场
//	public static final String MEDIA_TYPE_LIVE_ROOM = "LIVE_ROOM";								// 直播间
	
	/*虚拟电视台直播流*/
//	public static final String VIRTUAL_STREAM = "rtmp://192.168.1.154/live/virtualstream";
	/*虚拟电视台播放时间*/
//	public static final String VIRTUAL_CHANNEL_START = "12:00:00";
	
//	public static final String PAGE_MEDIA_MORE = "16";
	
	
	/* 是否有效 */
	public static final boolean IS_ACTIVE = false;
	
	/* 页面显示尺寸 */
	public static final String PAGE_SIZE = "10";
	
	/* 用户身份类型 */
//	public static final String USER_CATAGARY = "2";//1 学生 2 教师
	
	/* 是否置顶 */
	public static final boolean IS_SET_TOP_TRUE = true;
	public static final boolean IS_SET_TOP_FALSE = false;
	
	/* 是否加载数据 */
	public static final String LOADDATA_TRUE = "true";
	public static final String LOADDATA_FALSE = "false";
	
	/* 视频上传状态 */
	public static final String MEDIA_STATUS_UPLOADED = "1";//已上传
	public static final String MEDIA_STATUS_TRANSCODING_SUCCESS = "2";//转码成功，可播放
	public static final String MEDIA_STATUS_TRANSCODING_FAILURE = "3";//转码失败
	public static final String MEDIA_STATUS_TRANSCODING_SD_FAILURE = "4";//转码标清失败
	public static final String MEDIA_STATUS_TRANSCODING_HD_FAILURE = "5";//转码高清失败
	public static final String MEDIA_STATUS_TRANSCODING = "6";//转码中。。。
	
	/* 视频审核状态 */
	public static final String MEDIA_AUDIT_STATUS_UNAUDIT = "1";//未审核
	public static final String MEDIA_AUDIT_STATUS_AUDITED = "2";//已审核
	public static final String MEDIA_AUDIT_STATUS_UNPASSED = "3";//审核未过
	
	/* 查询的视频判断 */
	public static final String MEDIAS_HAS_ALL = "ALL";//包括自身视频
	public static final String MEDIAS_HAS_OTHERS = "OTHERS";//不含自身视频
	
	// 节目时间表类型
//	public static final String PROGRAM_SCHEDULE_TYPE_COMPUS_STATION_PROGRAM = "1";//校台节目
//	public static final String PROGRAM_SCHEDULE_TYPE_LIVE_ROOM = "2";//直播间
//	public static final String PROGRAM_SCHEDULE_TYPE_DVD = "3";//DVD直播间
	
	// 视频查询方式
//	public static final String QRYMEDIAWAY_BY_TITLE = "1";//按标题
//	public static final String QRYMEDIAWAY_BY_DESCRIPTION = "2";//按内容
	
	// 菜单类型
//	public static final String MENUITEM_TYPE_BACKGROUND = "0";//后台菜单
//	public static final String MENUITEM_TYPE_FOREGROUND = "1";//前台菜单
	
	// 用户类型
	public static final String USER_TYPE_FOREGROUND = "1";//前台用户
	public static final String USER_TYPE_BACKGROUND = "2";//后台用户
	
	// 直播状态
//	public static final String LIVE_ROOM_STATUS_REPALY = "1";//回看
//	public static final String LIVE_ROOM_STATUS_LIVING = "2";//正在播出
//	public static final String LIVE_ROOM_STATUS_OTHER = "3";//其他情况
	
	//直播流
//	public static final String LIVE_ROOM__STREAM = "rtmp://192.168.1.37/live/";//直播流
	
	//直播频道
//	public static final String LIVE_ROOM__FILE_VIDEO = "livestream1";//校台节目
//	public static final String LIVE_ROOM__FILE_MOBILE = "livestream2";//直播间
//	public static final String LIVE_ROOM__FILE_DVD = "livestream3";//DVD直播间
//	public static final int LIVE_ROOM_CHANNEL_NUM = 21;//直播频道个数
	// 文件夹类型
	public enum FolderType {
		UPLOAD_FILE("fileUpload"),                     	//上传文件
		UPLOAD_IMAGE("imageUpload"),					//上传图片
		UPLOAD_AUDIO("audioUpload"),					//上传音频
		UPLOAD("upload"),								//上传视频
		TRANSCODING("transcoding");						//视频转码
		private final String folder;
		private FolderType(String folderName) {
			this.folder = folderName;
		}
		public String toString() {
			return this.folder;
		}
	}
	// 视频来源
	public static final String MEDIA_FROM_SYSTEM = "1";//系统视频
	public static final String MEDIA_FROM_OUTTER = "2";//外部视频
	public static final String MEDIA_FROM_IMPORT = "3";//导入视频
	
	// 评论信息
	public static final String CATEGORY_PERMIT = "permit";//允许评论
	public static final String CATEGORY_PROHIBIT = "prohibit";//禁止评论
	
	//收藏夹
//    public static final int FAVORITE_PAGE_SIZE = 3;//收藏夹分页每页显示条数
//    public static final int PAGE_BAR_SIZE = 10;//收藏夹分页条页数
    //专辑页面
//    public static final int TEACHING_LATEST_MEDIO_NUM = 9;//专辑最新视频数
//    public static final int TEACHING_HOT_MEDIO_NUM = 10;//专辑最热视频数
//    public static final int TEACHING_CATEGORY_MEDIO_NUM = 6;//专辑分类视频数
//    public static final String PAGE_MEDIA_NUM = "15";//更多视频/新闻 每页视频数
//    public static final int TEACHING_PAGE_SIZE = 20;//专辑页面每页视频组数
//    public static final int FIRST_MEDIA = 1;//第一个视频
//    public static final int TEACHING_PLAY_MEDIO_NUM = 10;//专辑播放页面每页视频数
    //搜索
//    public static final int SEARCH_PAGE_MEDIA = 15;//专辑播放页面每页视频数
//    public static final int SEARCH_PAGE_PICTURE = 15;//专辑播放页面每页图片数
//    public static final int SEARCH_PAGE_NEWS = 13;//专辑播放页面每页视频数
//    public static final int SEARCH_PAGE_BAR_SIZE = 10;//专辑播放页面每页视频数
    
    //vpe project & resource status draft草稿 main正式
    public static final String PROJECT_DRAFT = "0";
    public static final String PROJECT_MAIN = "1";
    public static final int PROJECT_PAGESIZE = 10;
    
    //vpe project & userCenter use
    /**
     * 个人中心 视频、图片、音频 每页显示15条记录
     */
    public static final int USERCNTER_PAGE_SIZE = 15;
    
    /**
     * 阿里云上传相关参数
     */
    public static final String ACCESS_ID = "VTBfH9p5FbI8QQFa";
    public static final String ACCESS_KEY = "PkzlLEAOfdj9FVStMkePSTFE683nYL";
   // public static final String OSS_ENDPOINT = "http://oss-cn-beijing.aliyuncs.com/";
   // public static final String OSS_ENDPOINT = "http://oss-cn-beijing-internal.aliyuncs.com/";
    public static final String OSS_ENDPOINT = FileConfig.getOssEndpoint();
    // 用户客户端系统类型
    public static final String COMPUTER = "0";// PC端
    public static final String PHONE = "1";// 手机端
    // 上传资源来源
    public static final String UPLOAD_FROM_WX = "weixin";// 资源来自微信

    public static final String RES_SHOW = "1";// 资源限制为显示
    
    public static final String PACKAGE_FINISHED = "1";// 工程打包完成
    public static final String PACKAGE_READY = "0";// 工程未进行打包状态
    
    public static final String JENKINSURL = "buildByToken/buildWithParameters";
    
}
