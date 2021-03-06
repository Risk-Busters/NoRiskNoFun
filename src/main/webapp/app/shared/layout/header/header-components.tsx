import React from 'react';
import {Translate} from 'react-jhipster';
import {NavbarBrand, NavItem, NavLink} from 'reactstrap';
import {NavLink as Link} from 'react-router-dom';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import appConfig from 'app/config/constants';

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <img src="content/images/TODO.svg" alt="Logo"/>
  </div>
);

export const Brand = props => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <BrandIcon/>
    <span className="brand-title">
      <Translate contentKey="global.title">NoRiskNoFun</Translate>
    </span>
    <span className="navbar-version">{appConfig.VERSION}</span>
  </NavbarBrand>
);

export const Home = props => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home"/>
      <span>
        <Translate contentKey="global.menu.home">Home</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const Projects = props => (
  <NavItem>
    <NavLink tag={Link} to="/entity/project" className="d-flex align-items-center">
      <FontAwesomeIcon icon="object-group"/>
      <span>
        <Translate contentKey="global.menu.projects"/>
      </span>
    </NavLink>
  </NavItem>
);
