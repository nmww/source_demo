# resource
git@github.com:nmww/resource.git


Quick setup — if you’ve done this kind of thing before
 Set up in Desktop	or	
HTTPS
SSH

git@github.com:nmww/resource.git
We recommend every repository include a README, LICENSE, and .gitignore.
### …or create a new repository on the command line
```
 echo "# resource" >> README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin git@github.com:nmww/resource.git
git push -u origin master
```
### …or push an existing repository from the command line
```
 git remote add origin git@github.com:nmww/resource.git
git push -u origin master
```
### …or import code from another repository

You can initialize this repository with code from a Subversion, Mercurial, or TFS project.

****
# student1510

- test how to use
- master


```
git add . // 要add的文件，. 表示添加所有
git commit -m 'add files' // 提交更新
git push origin master  // push到远端master上

```
test
# …or create a new repository on the command line
```
 echo "# weizhang.github.io" >> README.md
 git init
 git add README.md
 git commit -m "first commit"
 git remote add origin git@github.com:nmww/weizhang.github.io.git
 git push -u origin master
```

# …or push an existing repository from the command line
```
  git remote add origin git@github.com:nmww/weizhang.github.io.git
  git push -u origin master
```
# …or import code from another repository
- You can initialize this repository with code from a Subversion, Mercurial, or TFS project.

# 回复到历史版本

* https://blog.csdn.net/sinat_26367835/article/details/45047411
* https://blog.csdn.net/yxys01/article/details/78454315?locationnum=10&fps=1

```
1、 查找历史版本

       使用git log命令查看所有的历史版本，获取你git的某个历史版本的id
       $ git log --pretty=oneline

       假设查到历史版本的id是fae6966548e3ae76cfa7f38a461c438cf75ba965。

2、 恢复到历史版本

$ git reset --hard fae6966548e3ae76cfa7f38a461c438cf75ba965
1
3、 把修改推到远程服务器

$ git push -f -u origin master  
```