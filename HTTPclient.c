#include <string.h>
#include "unp.h"
//#define MAXLINE 1000
int main(int argc, char **argv)
{
	int sockfd,n,counter=0,listenfd;
	char recvline[MAXLINE+1];
	struct sockaddr_in servaddr;
	if(argc != 3)
		{
		err_quit("usage:a.out <IPaddress>");
		}
	if((sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0)
		err_sys("socket error");
	bzero(&servaddr,sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_port = htons(atoi(argv[2]));
	if (inet_pton(AF_INET,argv[1], &servaddr.sin_addr) <= 0)
		err_quit("inet_pton error for %s ", argv[1]);
	if(connect(sockfd,(SA *) &servaddr, sizeof(servaddr)) < 0 )
		err_sys("connect error");
	
	Write(sockfd,"GET /index http/1.1",strlen("GET /index http/1.1"));
	Write(sockfd,"\r\n\r\n",strlen("\r\n\r\n"));
	while ( (n= read(sockfd, recvline, MAXLINE))>0)
	{
		counter++;
		recvline[n] = 0;
		if(fputs(recvline,stdout)==EOF)
			err_sys("fputs error");
	}
	if (n<0)
		err_sys ("read error");
	printf("counter = %d\n", counter);
	printf("respond is %s",recvline);
	exit(0);
}
