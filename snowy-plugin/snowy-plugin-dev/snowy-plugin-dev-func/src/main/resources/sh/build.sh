#!/bin/bash

# 获取脚本所在的目录
script_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# 设置前端和后端代码的仓库地址和目录
frontend_repo="http://myk:Jianke66@10.0.33.102:8099/jiankeTeam/jkepc.git"
backend_repo="http://myk:Jianke66@10.0.33.102:8099/jiankeTeam/single-server.git"
yunpan_repo="http://myk:Jianke66@10.0.33.102:8099/jiankeTeam/yunpan.git"
frontend_branch="epc测试"
backend_branch="dev-epc-yezi"
yunpan_branch="epcDevCopy"

frontend_dir="web"
backend_dir="server"
yunpan_dir="yunpan"

# 设置Maven和Node.js的相对路径
#maven_path="$script_dir/relative/path/to/maven/bin"
#node_path="$script_dir/relative/path/to/node/bin"
#export PATH=$PATH:$maven_path
#export PATH=$PATH:$node_path

# 检查是否安装了git、npm、mvn
command -v git >/dev/null 2>&1 || { echo >&2 "Git 未安装，请先安装 Git."; exit 1; }
command -v npm >/dev/null 2>&1 || { echo >&2 "Node.js 和 npm 未安装，请先安装 Node.js."; exit 1; }
command -v mvn >/dev/null 2>&1 || { echo >&2 "Maven 未安装，请先安装 Maven."; exit 1; }

# 检查 frontend_dir 目录是否存在，如果存在则清空目录
if [ -d "$script_dir/$frontend_dir" ]; then
    rm -rf "$script_dir/$frontend_dir"
fi

# 检查 backend_dir 目录是否存在，如果存在则清空目录
if [ -d "$script_dir/$backend_dir" ]; then
    rm -rf "$script_dir/$backend_dir"
fi

# 检查 yunpan_dir 目录是否存在，如果存在则清空目录
if [ -d "$script_dir/$yunpan_dir" ]; then
    rm -rf "$script_dir/$yunpan_dir"
fi

# 拉取前端代码指定分支
git clone -b "$frontend_branch" "$frontend_repo" "$script_dir/$frontend_dir"

# 进入前端目录
cd "$script_dir/$frontend_dir"

# 使用npm ci安装依赖并构建前端代码
npm install && npm run build-test

# 检查npm构建是否成功
if [ $? -ne 0 ]; then
    echo "前端epc项目构建失败"
    exit 1
fi

# 返回到脚本所在目录
cd "$script_dir"

# 拉取云盘代码指定分支
git clone -b "$yunpan_branch" "$yunpan_repo" "$script_dir/$yunpan_dir"

# 进入云盘目录
cd "$script_dir/$yunpan_dir"

# 使用npm ci安装依赖并构建前端代码
npm install && npm run build

# 检查npm构建是否成功
if [ $? -ne 0 ]; then
    echo "前端云盘项目构建失败"
    exit 1
fi

# 返回到脚本所在目录
cd "$script_dir"

# 拉取后端代码指定分支
git clone -b "$backend_branch" "$backend_repo" "$script_dir/$backend_dir"

## 设置后端的目标目录
#target_dir="$script_dir/$backend_dir/snowy-web-app/src/main/resources/static"
#
## 检查目标目录是否为空，如果不为空则清空
#if [ "$(ls -A $target_dir)" ]; then
#    rm -r "$target_dir"/*
#fi
#
## 复制前端构建包到后端static目录下
#cp -r "$script_dir/$frontend_dir/dist/"* "$target_dir"
#
#cp -r "$script_dir/$frontend_dir/dist/"* "$target_dir"

# 进入后端目录
cd "$script_dir/$backend_dir"

# 使用Maven构建后端代码
mvn clean package -DskipTests

# 检查Maven构建是否成功
if [ $? -ne 0 ]; then
    echo "后端构建失败"
    exit 1
fi

# 打印完成消息
echo "构建完成"

# 返回到脚本所在目录
cd "$script_dir"

# 构建 Docker 镜像
docker build -t epc:latest .

echo "镜像构建完成"

# 使用 Docker Compose 启动容器
docker-compose -f docker-compose.yml up -d

echo "epc单体服务启动成功"