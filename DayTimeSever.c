#include "unp.h"
#include <time.h>
#include <string.h>

#define IndexPage "this is the home page"
#define ErrorPage "this is the error page"
int main(int argc, char **argv)
{
	char temp='n';
	int listenfd, connfd,n=0,counter=0;
	struct sockaddr_in servaddr;//address structure to hold this sever's
				    //  address
	char buff[MAXLINE];
	char cmd[50],path[200],vers[20];
	char path1[500]={"."};
	char recvline[MAXLINE];
	time_t ticks; //required to calculate date and time
	printf("hjhjh\n");
	if(argc != 2)
		err_quit("usage:<Program Name> <Port No.>");
	listenfd = Socket(AF_INET, SOCK_STREAM, 0);//create socket
	bzero(&servaddr,sizeof(servaddr));
	servaddr.sin_family	=AF_INET;
	servaddr.sin_addr.s_addr=htonl(INADDR_ANY);//(INADDR_ANY);
	servaddr.sin_port	=htons(atoi(argv[1]));
	
	Bind(listenfd, (SA *) &servaddr, sizeof(servaddr));//connect the socket
						// to an external interface;
	Listen(listenfd,LISTENQ);
	for( ; ; )
	{
		path1[0]='.';
		path1[1]='\0';
		connfd = Accept(listenfd,(SA *)NULL, NULL);
		while ( (n= read(connfd, recvline, MAXLINE))>0)
		{
                	counter++;
                	recvline[n] = 0;
			printf("%s",recvline);
                	if(strstr(recvline,"\r\n\r\n")>0)
			{
				printf("Read done\n");
				break;
			}
        	}
		/*
		printf("Accept it or not? n/y\n");
		scanf("%c",&temp);
		getchar();
		if(temp=='y')
		{	
			ticks = time(NULL);
			printf("one client connecting:\n");
			printf("accept\n");
			snprintf(buff, sizeof(buff),"%.24s\r\n",ctime(&ticks));
			Write(connfd, buff ,strlen(buff));
		}
		else
		{
			printf("declined");
			snprintf(buff,sizeof(buff),"Declined");
			Write(connfd,buff,strlen(buff));
		}
		*/
		sscanf(recvline,"%s %s %s",cmd,path,vers);
		strcat(path1,path);
		printf("Path1:%s",path1);
		if(strcmp(path1,"./index")==0)
			Write(connfd,IndexPage,strlen(IndexPage));
			else if(strcmp(path1,"./Error")==0) 
                        	Write(connfd,ErrorPage,strlen(ErrorPage));
			else Write(connfd,"Page can't find",strlen("Page can't find"));
		Close(connfd);
	}
}
