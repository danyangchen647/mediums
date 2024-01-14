目标：
        medium是很多it博主分享知识的收费Blog，本次挑战目标是建立一个web应用，实现两个功能，
        点击"Create pdf files"按钮，抓取https://medium.com/?tag=software-engineering的top 10 pages并逐段翻译成完整中英文对照文件，
        保存成对应pdf文件。生成文件后，点击"Download"按钮，下载生成的10个pdf文件的zip包到本地。

要求：
 * 编程语言不限，第三方框架不限，操作系统不限
 * github提交时间为24小时内
 * 开发和网络环境自备
 
评价标准：
 * 工程完成度
 * 代码量和质量
 * 用户体验
 * 执行效率
 
工程提交：
2024年1月6日18:00前提交工程和README.md到自建github账户的私有仓库中，登录密码统一设置为（不含引号）：“2014HappyNewYear”，发送工程链接到微信群。

评价体系：
1: 投票 70分
2: 评委 30分（有一票否决权，拒绝各种作弊，AI除外）

第一版完成思路
1. 抓取https://medium.com/tag/software-engineering/archive网站 10 more read 文章url
2. 将top10 文章抓取article中的内容并且翻译成中文
3. 保存为pdf文件,同时打包

未解决的问题：
1. 文章有些返回307 HTTP error fetching URL. Status=307, URL=https://towardsdatascience.com/10-common-software-architectural-patterns-in-a-nutshell-a0b47a1e9013
2. 百度翻译api ，翻译未能完成。


新的思路
1. 引入selenium 使用无头模式，模拟访问，同时引入沉浸式翻译插件 注 chrome 浏览器与 selenium 版本必须一致
2. 将top10 文章逐步点击进入，然后通过代码 driver.getPageSource() 拉取到 html 并保存临时文件
3. 使用wkhtmltopdf将html临时文件转换成pdf
4. 打包成zip包

未解决的问题: 
代码driver.getPageSource() 拉取到 html 并保存临时文件打开404，转换pdf出错


