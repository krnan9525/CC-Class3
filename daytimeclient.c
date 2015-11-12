#include "unp.h"
int main()
{
	int sockfd,n,counter=0;
	char recvline[MAXLINE+1];
	struct sckaddr_in servaddr;
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
	while ( (n= read(sockfd, recvline, MAXLINE))>0)
	{
		counter++;
		recvline[n] = 0;
		if(fputs(recvline,stdout)==EOF)
			err_sys("fputs error");
	}
	if (n<0)
		err_sys ("read error");
	prentf("counter = %d\n", counter);
	exit(0);
}
