//
// Created by cuiqing on 2022/2/9.
//

/*
 * drizzleDumper Code By Drizzle.Risk
 * file: drizzleDumper.c
 */

#include <strings.h>
#include "DexDumper.h"

//int main(int argc, char *argv[]) {
//
//    printf("[>>>]  This is drizzleDumper [<<<]\n");
//    printf("[>>>]    code by Drizzle     [<<<]\n");
//    printf("[>>>]        2016.05         [<<<]\n");
////    exit(0);
//    if(argc <= 1)
//    {
//        printf("[*]  Useage : ./drizzleDumper package_name wait_times(s)\n[*]  The wait_times(s) means how long between the two Scans, default 0s  \n[*]  if successed, you can find the dex file in /data/local/tmp\n[*]  Good Luck!\n");
//        return 0;
//    }
//
//    //Check root
//    if(getuid() != 0)
//    {
//        printf("[*]  Device Not root!\n");
//        return -1;
//    }
//
//    double wait_times = 0.01;
//    if(argc >= 3)
//    {
//        wait_times = strtod(argv[2], NULL);
//        printf("[*]  The wait_times is %ss\n", argv[2]);
//    }
//    char *package_name = argv[1];
//
//    printf("[*]  Try to Find %s\n", package_name);
//
//    uint32_t pid = -1;
//
//    int i = 0;
//    int mem_file;
//    uint32_t clone_pid;
//    char *extra_filter;
//    char *dumped_file_name;
//
//    /*
//     * Into the loop
//     */
//    while(1)
//    {
//        //wait some time
//        sleep(wait_times);
//        pid = -1;
//        pid = get_process_pid(package_name);
//
//        //find process
//        if(pid < 1 || pid == -1)
//        {
//            continue;
//        }
//        printf("[*]  pid is %d\n", pid);
//
//        //find cloned process
//        clone_pid = get_clone_pid(pid);
//        if(clone_pid <= 0)
//        {
//            continue;
//        }
//        printf("[*]  clone pid is %d\n", clone_pid);
//
//        memory_region memory;
//        //ptrace cloned process
//        printf("[*]  ptrace [clone_pid] %d\n", clone_pid);
//        mem_file = attach_get_memory(clone_pid);
//        if(mem_file == -10201)
//        {
//            continue;
//        }
//        else if(mem_file == -20402)
//        {
//            //continue;
//        }
//        else if(mem_file == -30903)
//        {
//            //continue
//        }
//
//        /*
//         * Begin Scanning
//         */
//        dumped_file_name = malloc(strlen(static_safe_location) + strlen(package_name) + strlen(suffix));
//        sprintf(dumped_file_name, "%s%s%s", static_safe_location, package_name, suffix);
//        printf("[*]  Scanning dex ...\n");
//        if(find_magic_memory(clone_pid, mem_file, &memory, dumped_file_name) <= 0)
//        {
//            printf("[*]  The magic was Not Found!\n");
//            ptrace(PTRACE_DETACH, clone_pid, NULL, 0);
//            close(mem_file);
//            continue;
//        }
//        else
//        {
//            /*
//             * Successed & exit
//             */
//            close(mem_file);
//            ptrace(PTRACE_DETACH, clone_pid, NULL, 0);
//            break;
//        }
//    }
//
//    printf("[*]  Done.\n\n");
//    return 1;
//}


/**
* 递归删除目录(删除该目录以及该目录包含的文件和目录)
* @dir:要删除的目录绝对路径
*/
int remove_dir(const char *dir)
{
    char cur_dir[] = ".";
    char up_dir[] = "..";
    char dir_name[128];
    DIR *dirp;
    struct dirent *dp;
    struct stat dir_stat;

    // 参数传递进来的目录不存在，直接返回
    if ( 0 != access(dir, F_OK) ) {
        return 0;
    }

    // 获取目录属性失败，返回错误
    if ( 0 > stat(dir, &dir_stat) ) {
        perror("get directory stat error");
        return -1;
    }

    if ( S_ISREG(dir_stat.st_mode) ) {	// 普通文件直接删除
        remove(dir);
    } else if ( S_ISDIR(dir_stat.st_mode) ) {	// 目录文件，递归删除目录中内容
        dirp = opendir(dir);
        while ( (dp=readdir(dirp)) != NULL ) {
            // 忽略 . 和 ..
            if ( (0 == strcmp(cur_dir, dp->d_name)) || (0 == strcmp(up_dir, dp->d_name)) ) {
                continue;
            }

            sprintf(dir_name, "%s/%s", dir, dp->d_name);
            remove_dir(dir_name);   // 递归调用
        }
        closedir(dirp);

        rmdir(dir);		// 删除空目录
    } else {
        perror("unknow file type!");
    }

    return 0;
}


int mk_dir(const char *dir)
{
    if (opendir(dir) != NULL)//判断目录
    {
        printf("[+] create package dir %s exist, Delete first! \n", dir);
        int ret = remove_dir(dir);
        if (ret < 0) {
            return -1;
        }
    }
    int ret = mkdir(dir, MODE);//创建目录
    if (ret != 0)
    {
        return -1;
    }
    printf("[+] create package dir %s sucess!\n", dir);

    return 0;
}

int main(int argc, char *argv[]) {

    printf("[>>>]  This is drizzleDumper [<<<]\n");
    printf("[>>>]    code by Drizzle     [<<<]\n");
    printf("[>>>]        2016.05         [<<<]\n");
//    exit(0);
    if(argc <= 1)
    {
        printf("[*]  Useage : ./drizzleDumper package_name wait_times(s)\n[*]  The wait_times(s) means how long between the two Scans, default 0s  \n[*]  if successed, you can find the dex file in /data/local/tmp\n[*]  Good Luck!\n");
        return 0;
    }

    //Check root
    if(getuid() != 0)
    {
        printf("[*]  Device Not root!\n");
        return -1;
    }

    double wait_times = 0.01;
    if(argc >= 3)
    {
        wait_times = strtod(argv[2], NULL);
        printf("[*]  The wait_times is %ss\n", argv[2]);
    }
    char *package_name = argv[1];

    printf("[*]  Try to Find %s\n", package_name);

    int pid = -1;

    int i = 0;
    int mem_file;
    int clone_pid;
    char *extra_filter;
    char *dumped_package_dir;

    /*
     * Into the loop
     */
        //wait some time
        sleep(wait_times);
        pid = -1;
        pid = get_process_pid(package_name);
        printf("[+] get_process_pid:%d.\n", pid);
        //find process
        if(pid < 1 || pid == -1)
        {
            printf("[Warning] can not found process.\n");
            exit(0);
        }
        printf("[*]  pid is %d\n", pid);

        //find cloned process
        clone_pid = get_clone_pid(pid);
        printf("[+] get_clone_pid:%d.\n", clone_pid);
        if(clone_pid <= 0)
        {
            printf("[Warning] can not get clone pid.\n");
            exit(0);
        }
        printf("[*]  clone pid is %d\n", clone_pid);

        memory_region memory;
        //ptrace cloned process
//        printf("[*]  ptrace [clone_pid] %d\n", clone_pid);

        uint32_t ppid = pid;
        mem_file = attach_get_memory(clone_pid);
        if(mem_file == -10201)
        {
            printf("[Warning] can not attach_get_memory.\n");
            exit(0);
        }
        else if(mem_file == -20402)
        {
            //continue;
        }
        else if(mem_file == -30903)
        {
            //continue
        }

        /*
         * Begin Scanning
         */
        dumped_package_dir = malloc(strlen(static_safe_location) + strlen(package_name));
        sprintf(dumped_package_dir, "%s%s", static_safe_location, package_name);
        printf("[*]  Scanning dex ...\n");
        mk_dir(dumped_package_dir);
        if(find_magic_memory(ppid, mem_file, &memory, dumped_package_dir) <= 0)
        {
            printf("[*]  The magic was Not Found!\n");
            //ptrace(PTRACE_DETACH, ppid, NULL, 0);
            close(mem_file);
            printf("[*] can not found magic memory.\n");
            exit(0);
        }
        else
        {
            /*
             * Successed & exit
             */
            close(mem_file);
            //ptrace(PTRACE_DETACH, ppid, NULL, 0);
        }

    printf("[*]  Done.\n\n");
    return 1;
}

int get_clone_pid(int service_pid)
{
    DIR *service_pid_dir;
    char service_pid_directory[1024];
    sprintf(service_pid_directory, "/proc/%d/task/", service_pid);

    if((service_pid_dir = opendir(service_pid_directory)) == NULL)
    {
        return -1;
    }

    struct dirent* directory_entry = NULL;
    struct dirent* last_entry = NULL;

    while((directory_entry = readdir(service_pid_dir)) != NULL)
    {
        last_entry = directory_entry;
    }

    if(last_entry == NULL)
        return -1;

    int pid = atoi(last_entry->d_name);
    closedir(service_pid_dir);

    return pid;
}

int get_process_pid(const char *target_package_name)
{
    char self_pid[10];
    sprintf(self_pid, "%u", getpid());

    DIR *proc = NULL;

    if((proc = opendir("/proc")) == NULL) {
        printf("[Warning] can not open proc dir.\n");
        return -1;
    }

    struct dirent *directory_entry = NULL;
    while((directory_entry = readdir(proc)) != NULL)
    {

        if (directory_entry == NULL)
            return -1;

        if (strcmp(directory_entry->d_name, "self") == 0 || strcmp(directory_entry->d_name, self_pid) == 0)
            continue;

        char cmdline[1024];
        snprintf(cmdline, sizeof(cmdline), "/proc/%s/cmdline", directory_entry->d_name);
        FILE *cmdline_file = NULL;
        if((cmdline_file = fopen(cmdline, "r")) == NULL)
            continue;

        char process_name[1024];
        fscanf(cmdline_file, "%s", process_name);
        fclose(cmdline_file);

        // printf("[+++] targetPack:%s package:%s pid:%s \n", target_package_name, process_name, directory_entry->d_name);
        if(strcmp(process_name, target_package_name) == 0)
        {
            char *dName = directory_entry->d_name;
            int pid =  atoi(dName);
            printf("[******] found targetPack:%s package:%s pidStr:%s pid:%d\n", target_package_name, process_name, dName, pid);
            closedir(proc);
            return pid;
        }
    }

    closedir(proc);
    return -1;
}





int searchAndDump(const char *buffer, int count, int64_t mem_start,int64_t mem_end,const char *dumpPackageDir, char* mem_info)
{
    for (int i = 0; i < count; ++i) {
        if (buffer[i]== 0x64) {
            if ((i+1 < count && buffer[i+1] == 0x65)
                &&(i+2 < count && buffer[i+2] == 0x78)
                &&(i+3 < count && buffer[i+3] == 0x0a)
                &&(i+0x3c < count && buffer[i+0x3c] == 0x70)
                ){

                uint32_t fileSize = buffer[i+0x20] + (buffer[i+0x21] << 8) + (buffer[i+0x22] << 16) + (buffer[i+0x23] << 24);
                uint32_t mapSize = buffer[i+0x20] + (buffer[i+0x21] << 8) + (buffer[i+0x22] << 16) + (buffer[i+0x23] << 24);

                int offset = i;
                if (mem_start + fileSize + offset > mem_end){
                    continue;
                }
                char each_filename[254] = {0};
                char addr[20] = {0};

                strncpy(each_filename , dumpPackageDir , 200);	//防溢出
                sprintf(addr, "/0x%x", fileSize);
//                strncpy(each_filename , addr , 20);	//防溢出

                strncat(each_filename , addr , 20);
                strncat(each_filename , ".dex" , 4);


                printf("[******] found dex men_line: %s, offset:%x,fileSize:0x%x,filename:%s, memStart:0x%llx, memEnd:0x%llx\n", mem_info, i, fileSize, each_filename, mem_start, mem_end);
//                printf("[******] file size: %x %x %x %x\n", buffer[i+0x38], buffer[i+0x39], buffer[i+0x3A], buffer[i+0x3B]);
                if(dump_memory(buffer+i , fileSize, each_filename)  == 1)
                {
                    printf(" [+] dex dump into %s\n", each_filename);
                }

            }
        }
    }
    return -1;
}

int find_magic_memory(uint32_t clone_pid, int memory_fd, memory_region *memory , const char *file_name) {
    int ret = 0;
    char maps[2048];
    snprintf(maps, sizeof(maps), "/proc/%d/maps", clone_pid);

    FILE *maps_file = NULL;
    if((maps_file = fopen(maps, "r")) == NULL)
    {
        printf(" [+] fopen %s Error  \n" , maps);
        return -1;
    }

    char mem_line[1024];
    while(fscanf(maps_file, "%[^\n]\n", mem_line) >= 0)
    {
//        printf("maps line : %s \n",mem_line);
        char mem_address_start[10];
        char mem_address_end[10];
        char mem_info[1024];
        sscanf(mem_line, "%10[^-]-%10[^ ]%*s%*s%*s%*s%s", mem_address_start, mem_address_end,mem_info);

        uint64_t mem_start = strtoul(mem_address_start, NULL, 16);
        memory->start = mem_start;
        memory->end = strtoul(mem_address_end, NULL, 16);


        uint64_t len =  memory->end - memory->start;

        // 跳过特定的文件
        if (strstr(mem_info, "/data/dalvik-cache/") != NULL || strstr(mem_info, "/system/") != NULL) {
            continue;
        }
        if (strstr(mem_line, ".vdex") != NULL) {
            printf("line: %s, startStr:%s endStr:%s len: %lld ,start: %lld, end: %lld\n",mem_line, mem_address_start, mem_address_end, len, memory->start, memory->end);
        } else {
//            continue;
        }

        if(len <= 10000)
        {//too small
            //printf("[+] memory too small %lld %s\n", len, mem_info);
            continue;
        }
        else if(len >= 150000000)
        {//too big
            printf("[+] memory too big %lld %s\n", len, mem_info);
            continue;
        }

        char each_filename[254] = {0};
        char randstr[10] = {0};
        sprintf(randstr ,"%d", rand()%9999 );

        strncpy(each_filename , file_name , 200);	//防溢出
        strncat(each_filename , randstr , 10);
        strncat(each_filename , ".dex" , 4);

        lseek64(memory_fd , 0 , SEEK_SET);	//保险，先归零
        off_t r1 = lseek64(memory_fd , memory->start , SEEK_SET);
        if(r1 == -1)
        {
            //do nothing
            printf("[+] memory seek64 fail ");
        }
        else
        {
            char *buffer = malloc(len);
//            int readCount = 8;
            int readCount = len;
//            char buffer[readCount];
//            lseek64(memory_fd , 0 , SEEK_SET);
//            lseek64(memory_fd , memory->start + 0x1770, SEEK_SET);
            printf("[-] read ret meminfo: %s ,len: %lld ,start: %llx  buffer:%s\n",mem_info, len, memory->start, buffer);

            ssize_t readlen = read(memory_fd, buffer, readCount);
            printf("[--] read ret meminfo: %s ,len: %lld ,readlen: %d, start: %llx \n",mem_info, len, readlen, memory->start);
//            int32_t magic = (buffer[0] << 24) | (buffer[1] << 16) | (buffer[2] << 8) | buffer[3];
//            if(buffer[0] == 'd' && buffer[1] == 'e' && buffer[2] == 'x' && buffer[3] == '\n'  && buffer[4] == '0' && buffer[5] == '3')
//            char *index = strstr(buffer, "dex");
            int dexSize;
            uint32_t rr = searchAndDump(buffer, len, memory->start, memory->end, file_name, mem_line);
            //printf("[-] read ret meminfo: %s ,len: %lld ,readlen: %d, start: %llx  \n",mem_info, len, readlen, memory->start);
//            printf("[--] search mem buffer: %s , ret: %s \n", buffer+0x1770 ,index);
            if (readlen > 0 && rr > 0){
                //printf("[+] buffer: %x %x %x %x magic:%d meminfo: %s\n", buffer[0], buffer[1], buffer[2], buffer[3], magic, mem_info);
                //printf(" [++] find dex, len : %d , info : %s\n" , readlen , mem_info);
//                printf("[+] buffer:");
//                for(int j=0; j<readCount; j++)
//                {
//                    printf("%02x ", buffer[j]);
//                }
//                printf("\n");
            }
            if(0)
            {
                printf(" [+] find dex, len : %d , info : %s\n" , readlen , mem_info);
                DexHeader header;
                char real_lenstr[10]={0};
                memcpy(&header , buffer ,sizeof(DexHeader));
                sprintf(real_lenstr , "%x" , header.fileSize);
                long real_lennum = strtol(real_lenstr , NULL, 16);
                printf(" [+] This dex's fileSize: %d\n", real_lennum);


                if(dump_memory(buffer , len , each_filename)  == 1)
                {
                    printf(" [+] dex dump into %s\n", each_filename);
                    free(buffer);
                    continue;
                }
                else
                {
                    printf(" [+] dex dump error \n");
                }

            }
            free(buffer);
            memset(mem_line , 0 ,1024);
        }


//        lseek64(memory_fd , 0 , SEEK_SET);	//保险，先归零
//        r1 = lseek64(memory_fd , memory->start + 8 , SEEK_SET);//不用 pread，因为pread用的是lseek
//        if(r1 == -1)
//        {
//            continue;
//        }
//        else
//        {
//            char *buffer = malloc(len);
//            ssize_t readlen = read(memory_fd, buffer, len);
//
//            if(buffer[0] == 'd' && buffer[1] == 'e' && buffer[2] == 'x' && buffer[3] == '\n'  && buffer[4] == '0' && buffer[5] == '3')
//            {
//                printf(" [+] Find dex! memory len : %d \n" , readlen);
//                DexHeader header;
//                char real_lenstr[10]={0};
//                memcpy(&header , buffer ,sizeof(DexHeader));
//                sprintf(real_lenstr , "%x" , header.fileSize);
//                long real_lennum = strtol(real_lenstr , NULL, 16);
//                printf(" [+] This dex's fileSize: %d\n", real_lennum);
//
//                if(dump_memory(buffer , len , each_filename)  == 1)
//                {
//                    printf(" [+] dex dump into %s\n", each_filename);
//                    free(buffer);
//                    continue;	//如果本次成功了，就不尝试其他方法了
//                }
//                else
//                {
//                    printf(" [+] dex dump error \n");
//                }
//            }
//            free(buffer);
//        }
    }
    fclose(maps_file);
    return ret;
}

/*
 * Dump buffer from Mem to file.
 */
int dump_memory(const char *buffer , int len , char each_filename[])
{
    int ret = -1;
    FILE *dump = fopen(each_filename, "wb");
    if(fwrite(buffer, len, 1, dump) != 1)
    {
        ret = -1;
    }
    else
    {
        ret = 1;
    }

    fclose(dump);
    return ret;
}

// Perform all that ptrace magic
int attach_get_memory(uint32_t pid) {
    char mem[1024];
    bzero(mem,1024);
    snprintf(mem, sizeof(mem), "/proc/%d/mem", pid);

    // Attach to process so we can peek/dump
    int ret = -1;
//    ret = ptrace(PTRACE_ATTACH, pid, NULL, NULL);
    ret = 0;
    int mem_file;

    if (0 != ret)
    {
        int err = errno;	//这时获取errno
        if(err == 1) //EPERM
        {
            return -30903;	//代表已经被跟踪或无法跟踪
        }
        else
        {
            return -10201;	//其他错误(进程不存在或非法操作)
        }
    }
    else
    {
        if(!(mem_file = open(mem, O_RDONLY)))
        {
            return -20402;  	//打开错误
        }
    }
    return mem_file;
}