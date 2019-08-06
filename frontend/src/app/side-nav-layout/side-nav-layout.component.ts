import { Component, OnInit } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, share } from 'rxjs/operators';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';

@Component({
  selector: 'app-side-nav-layout',
  templateUrl: './side-nav-layout.component.html',
  styleUrls: ['./side-nav-layout.component.scss']
})
export class SideNavLayoutComponent implements OnInit {

  isHandset$: Observable<boolean>;

  constructor(private breakpointObserver: BreakpointObserver, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.isHandset$ = this.breakpointObserver.observe(Breakpoints.Handset)
      .pipe(
        map(result => result.matches),
        share()
      );
  }

  openLoginDialog(): void {
    const config = new MatDialogConfig();
    config.width = '300px';
    config.hasBackdrop = true;

    this.dialog.open(LoginDialogComponent, config);
  }
}
